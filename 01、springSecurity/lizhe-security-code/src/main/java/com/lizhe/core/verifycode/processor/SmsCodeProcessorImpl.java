package com.lizhe.core.verifycode.processor;

import com.lizhe.core.SecurityConstants;
import com.lizhe.core.verifycode.sender.SmsCodeSender;
import com.lizhe.core.verifycode.po.VerifyCode;
import com.lizhe.core.verifycode.generator.VerifyCodeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.HashMap;
import java.util.Map;

import static com.lizhe.core.verifycode.constont.VerifyCodeConstant.PHONE_NUMBER_PARAMETER_NAME;
import static com.lizhe.core.verifycode.constont.VerifyCodeConstant.VERIFY_CODE_GENERATOR_IMPL_SUFFIX;
import static com.lizhe.core.verifycode.constont.VerifyCodeTypeEnum.SMS;

/**
 * @author lz
 * @create 2020-05-18
 */
@Component
public class SmsCodeProcessorImpl extends AbstractVerifyCodeProcessor<VerifyCode> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Spring高级特性 自动装配
     * Spring会查找容器中所有{@link VerifyCodeGenerator}的实例并以 key=beanId,value=bean的形式注入到该map中
     * 这个特性的其实还得归纳到@Autowired的特性上，或者说@Autowired包涵了一些我们不知道的能力。
     */
    @Autowired
    private Map<String, VerifyCodeGenerator> verifyCodeGeneratorMap = new HashMap<>();

    @Autowired
    private SmsCodeSender smsCodeSender;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 生成短信验证码
     *
     * @param request
     * @return
     */
    @Override
    public VerifyCode generateVerifyCode(ServletWebRequest request) {
        VerifyCodeGenerator verifyCodeGenerator = verifyCodeGeneratorMap.get(SMS.getType() + VERIFY_CODE_GENERATOR_IMPL_SUFFIX);
        return verifyCodeGenerator.generateVerifyCode();
    }

    @Override
    public void save(ServletWebRequest request, VerifyCode verifyCode) {
        sessionStrategy.setAttribute(request, SecurityConstants.SMS_CODE_SESSION_KEY, verifyCode);
    }

    @Override
    public void send(ServletWebRequest request, VerifyCode verifyCode) {
        try {
            long phoneNumber = ServletRequestUtils.getRequiredLongParameter(request.getRequest(), PHONE_NUMBER_PARAMETER_NAME);
            smsCodeSender.send(verifyCode.getCode(), String.valueOf(phoneNumber));
        } catch (ServletRequestBindingException e) {
            throw new RuntimeException("手机号不能为空");
        }
    }
}
