package com.lizhe.core.verifycode.filter;

import com.lizhe.core.verifycode.exception.VerifyCodeException;
import com.lizhe.core.verifycode.po.VerifyCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Objects;

/**
 * 使用模板方法，抽象验证码的校验逻辑
 * @author lz
 * @date 2019/9/2
 * @desc VerifyCodeValidator
 */
public abstract class AbstractVerifyCodeValidator {

    protected SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private VerifyCodeValidatorHolder verifyCodeValidatorHolder;

    /**
     * 校验验证码
     * 1.从请求中获取传入的验证码
     * 2.从服务端获取存储的验证码
     * 3.校验验证码
     * 4.校验成功移除服务端验证码，校验失败抛出异常信息
     * 实现类（各种验证码的具体验证逻辑），对外提供根据验证码类型获取对应验证码校验bean的方法
     * @param request
     * @param verifyCodeType
     * @throws VerifyCodeException
     */
    public void validateVerifyCode(ServletWebRequest request, VerifyCodeType verifyCodeType) throws VerifyCodeException {
        String requestCode = getVerifyCodeFromRequest(request, verifyCodeType);

        /**
         *
         * 根据传入的verifyCodeType对象，获取对应的AbstractVerifyCodeValidator的实现，比如ImageCodeValidatorImpl或者
         * SmsCodeValidatorImpl
         */
        AbstractVerifyCodeValidator codeValidatorImpl = verifyCodeValidatorHolder.getVerifyCodeValidator(verifyCodeType);
        if (Objects.isNull(codeValidatorImpl)) {
            throw new VerifyCodeException("不支持的验证码校验类型: " + verifyCodeType);
        }
        // 执行xxxxCodeValidatorImpl对应的方法，获取session里(内存里)的VerifyCode对象
        VerifyCode storedVerifyCode = codeValidatorImpl.getStoredVerifyCode(request);

        codeValidatorImpl.validate(requestCode, storedVerifyCode);

        codeValidatorImpl.removeStoredVerifyCode(request);
    }

    /**
     * 校验验证码是否过期，默认进行简单的文本比对，子类可重写以校验传入的明文验证码和后端存储的密文验证码
     *
     * @param requestCode
     * @param storedVerifyCode
     */
    private void validate(String requestCode, VerifyCode storedVerifyCode) {
        if (StringUtils.isBlank(requestCode)) {
            throw new VerifyCodeException("验证码不能为空");
        }
        if ( Objects.isNull(storedVerifyCode) || StringUtils.equalsIgnoreCase(requestCode, storedVerifyCode.getCode())) {
            throw new VerifyCodeException("验证码错误");
        }
        if (Objects.isNull(storedVerifyCode) || storedVerifyCode.isExpired()) {
            throw new VerifyCodeException("验证码已失效，请重新生成");
        }


    }

    /**
     * 是从Session中还是从其他缓存方式移除验证码由子类自己决定
     *
     * @param request
     */
    protected abstract void removeStoredVerifyCode(ServletWebRequest request);

    /**
     * 是从Session中还是从其他缓存方式读取验证码由子类自己决定
     *
     * @param request
     * @return
     */
    protected abstract VerifyCode getStoredVerifyCode(ServletWebRequest request);


    /**
     * 默认从请求中获取验证码参数，可被子类重写
     *
     * @param request
     * @param verifyCodeType
     * @return
     */
    private String getVerifyCodeFromRequest(ServletWebRequest request, VerifyCodeType verifyCodeType) {
        try {
            return ServletRequestUtils.getStringParameter(request.getRequest(), verifyCodeType.getVerifyCodeParameterName());
        } catch (ServletRequestBindingException e) {
            throw new VerifyCodeException("非法请求，请附带验证码参数");
        }
    }

}
