package com.lizhe.core.verifycode.filter;

import com.lizhe.core.SecurityConstants;
import com.lizhe.core.verifycode.po.VerifyCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author lz
 * @create 2020-05-19
 */
@Component
public class ImageCodeValidatorImpl extends AbstractVerifyCodeValidator {

    /**
     * 删除session中的图形验证码对象
     * @param request
     */
    @Override
    protected void removeStoredVerifyCode(ServletWebRequest request) {
        sessionStrategy.removeAttribute(request, SecurityConstants.IMAGE_CODE_SESSION_KEY);

    }

    /**
     * 获取session中的verifyCode对象
     * @param request
     * @return
     */
    @Override
    protected VerifyCode getStoredVerifyCode(ServletWebRequest request) {
        return (VerifyCode) sessionStrategy.getAttribute(request, SecurityConstants.IMAGE_CODE_SESSION_KEY);
    }
}
