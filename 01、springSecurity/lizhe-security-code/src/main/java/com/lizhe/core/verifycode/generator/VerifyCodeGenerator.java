package com.lizhe.core.verifycode.generator;

import com.lizhe.core.verifycode.po.VerifyCode;

/**
 *  生成器生成4位验证码
 * @author lz
 * @create 2020-05-18
 */
public interface VerifyCodeGenerator<T extends VerifyCode> {

    /**
     *  生成图形和数字验证码接口方法。泛型继承VerifyCode
     * @return
     */
    T generateVerifyCode();
}
