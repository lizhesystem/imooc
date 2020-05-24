package com.lizhe.core.verifycode.processor;

import com.lizhe.core.SecurityConstants;
import com.lizhe.core.verifycode.po.ImageCode;
import com.lizhe.core.verifycode.generator.VerifyCodeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.lizhe.core.verifycode.constont.VerifyCodeTypeEnum.IMAGE;
import static com.lizhe.core.verifycode.constont.VerifyCodeConstant.VERIFY_CODE_GENERATOR_IMPL_SUFFIX;

/**
 * 实现验证码方法的实现类
 *
 * @author lz
 * @create 2020-05-18
 */

@Component
public class ImageCodeProcessorImpl extends AbstractVerifyCodeProcessor<ImageCode> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Spring高级特性 自动装配 启动的时候
     * Spring会查找容器中所有{@link VerifyCodeGenerator}的实例并以 key=beanId,value=bean的形式注入到该map中
     * 这个特性的其实还得归纳到@Autowired的特性上，或者说@Autowired包涵了一些我们不知道的能力。
     * <p>
     * 有了这个功能，咱们就不用导入@Autowired了,直接根据模板直接匹配对应的imageCodeGenerator或者smsCodeGenerator，然后执行构造！
     */
    @Autowired
    private Map<String, VerifyCodeGenerator> verifyCodeGeneratorMap = new HashMap<>();


    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();


    /**
     * 生成图形验证码，根据传入的参数得到对应的接口实现类，构造验证码方法
     *
     * @param request
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public ImageCode generateVerifyCode(ServletWebRequest request) {
        // (依赖搜索)找到对应的实现
        VerifyCodeGenerator<ImageCode> verifyCodeGenerator = verifyCodeGeneratorMap.get(IMAGE.getType() + VERIFY_CODE_GENERATOR_IMPL_SUFFIX);
        return verifyCodeGenerator.generateVerifyCode();
    }


    @Override
    public void save(ServletWebRequest request, ImageCode imageCode) {
        ImageCode ic = new ImageCode(imageCode.getCode(), null, imageCode.getExpireTime());
        sessionStrategy.setAttribute(request, SecurityConstants.IMAGE_CODE_SESSION_KEY, ic);
    }

    @Override
    public void send(ServletWebRequest request, ImageCode imageCode) {
        HttpServletResponse response = request.getResponse();

        try {
            ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
        } catch (IOException e) {
            logger.info("输出图形验证码:{}", e.getMessage());
        }
    }

    public static void main(String[] args) {
        String type = IMAGE.getType();
        System.out.println(type);
    }
}
