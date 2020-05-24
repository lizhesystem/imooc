package com.lizhe.core.verifycode.processor;

import com.lizhe.core.verifycode.po.VerifyCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 *  定义模板抽象类
 * @author lz
 * @create 2020-05-18
 */
public abstract class AbstractVerifyCodeProcessor<T extends VerifyCode> implements VerifyCodeProcessor {

    /**
     * 抽象类里继承接口的普通方法
     * @param request
     * 封装request和response的工具类，用它我们就不用每次传{@link javax.servlet.http.HttpServletRequest}
     */
    @Override
    public void sendVerifyCode(ServletWebRequest request) {
        // 定义三个抽象方法,来让继承该类的对象来实现方法
        T verifyCode = generateVerifyCode(request);
        save(request, verifyCode);
        send(request, verifyCode);
    }


    /**
     * 生成验证码的抽象方法
     *
     * @param request
     * @return
     */
    public abstract T generateVerifyCode(ServletWebRequest request);

    /**
     * 保存验证码的抽象方法
     *
     * @param request
     * @param verifyCode
     */
    public abstract void save(ServletWebRequest request, T verifyCode);

    /**
     * 发送验证码的抽象方法
     *
     * @param request
     * @param verifyCode
     */
    public abstract void send(ServletWebRequest request, T verifyCode);
}
