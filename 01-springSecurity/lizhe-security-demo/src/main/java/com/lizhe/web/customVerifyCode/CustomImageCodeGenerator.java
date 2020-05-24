package com.lizhe.web.customVerifyCode;

import com.lizhe.core.verifycode.po.ImageCode;
import com.lizhe.core.verifycode.generator.VerifyCodeGenerator;

/**
 * 测试：如果自定义生成验证码生成器是否优先使用自己实现的！
 * 注意@Component的value属性要和@ConditionOnMissingBean的name属性一致才能实现替换。
 *
 * @author lz
 * @create 2020/5/16
 */
//@Component("imageCodeGenerator") 先注释
public class CustomImageCodeGenerator implements VerifyCodeGenerator<ImageCode> {

    //@Override
    //public ImageCode generateImageCode() {
    //    System.out.println("调用自定义的代码生成器");
    //    return null;
    //}

    @Override
    public ImageCode generateVerifyCode() {
        System.out.println("调用自定义的代码生成器");
        return null;
    }
}
