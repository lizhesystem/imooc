package com.lizhe.core.verifycode.processor;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author lz
 * @create 2020-05-18
 */
public interface VerifyCodeProcessor {
    /**
     * 发送验证码逻辑
     * 1.   生成验证码
     * 2.   保存验证码
     * 3.   发送验证码
     * @param request
     * 封装request和response的工具类，用它我们就不用每次传{@link javax.servlet.http.HttpServletRequest}
     * 和{@link javax.servlet.http.HttpServletResponse}了
     *
     * ServletWebRequest：封装请求和响应
     */
    void sendVerifyCode(ServletWebRequest request);
}
