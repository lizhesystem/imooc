package com.lizhe.core.verifycode.controller;

import com.lizhe.core.verifycode.processor.VerifyCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.lizhe.core.verifycode.constont.VerifyCodeConstant.VERIFY_CODE_PROCESSOR_IMPL_SUFFIX;
import static com.lizhe.core.verifycode.constont.VerifyCodeTypeEnum.IMAGE;
import static com.lizhe.core.verifycode.constont.VerifyCodeTypeEnum.SMS;

/**
 * 生成验证码的接口
 *
 * @author lz
 * @create 2020-05-16
 */

@RestController
@RequestMapping("/verifyCode")
public class VerifyCodeController {

    /*
     *//**
     * Session读写工具类
     *//*
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    // 常量key
    public static final String IMAGE_CODE_SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    public static final String SMS_CODE_SESSION_KEY = "SESSION_KEY_SMS_CODE";

    //@Autowired
    //private SecurityProperties securityProperties;


    *//**
     * 验证码生成接口（面向抽象编程以适应变化），注意这里注入的是接口类，如果有人实现了这个接口
     * 添加了@Component注入容器的话，会首先使用这个实现类。
     *//*
    @Autowired
    private VerifyCodeGenerator<ImageCode> imageCodeGenerator;

    @Autowired
    private VerifyCodeGenerator<VerifyCode> smsCodeGenerator;

    @Autowired
    private SmsCodeSender smsCodeSender;

    *//**
     * 1:生成图片验证码
     * 2：将图形验证码放到Session中
     * 3：将图形响应给前端
     *
     * @param req
     * @param rsp
     *//*
    @RequestMapping("/image")
    public void imageCode(HttpServletRequest req, HttpServletResponse rsp) throws IOException {
        // 首先读取URL参数中的width/height，如果没有则使用配置文件中的
        //int width = ServletRequestUtils.getIntParameter(req, "width", securityProperties.getCode().getImage().getWidth());
        //int height = ServletRequestUtils.getIntParameter(req, "height", securityProperties.getCode().getImage().getHeight());
        // 生成图形验证码 默认60秒--（使用面向接口的方式生成验证码）
        //ImageCode imageCode = imageCodeGenerator.generateImageCode(width, height,
        //        securityProperties.getCode().getImage().getStrLength(),
        //        securityProperties.getCode().getImage().getDurationSeconds());
        // Session读写工具类, 第一个参数写法固定
        // (重构)
        ImageCode imageCode = imageCodeGenerator.generateVerifyCode();
        sessionStrategy.setAttribute(new ServletWebRequest(req), IMAGE_CODE_SESSION_KEY, imageCode);
        ImageIO.write(imageCode.getImage(), "JPEG", rsp.getOutputStream());
    }


    @GetMapping("/sms")
    public void smsCode(HttpServletRequest req, HttpServletRequest rsp) throws ServletRequestBindingException {
        // 获取请求接口中的phoneNumber参数值
        Long phoneNumber = ServletRequestUtils.getRequiredLongParameter(req, "phoneNumber");
        // 生成短信验证码放到session里，调用send方法发送这里可以调用第三方的接口来发送
        VerifyCode verifyCode = smsCodeGenerator.generateVerifyCode();
        sessionStrategy.setAttribute(new ServletWebRequest(req), SMS_CODE_SESSION_KEY, verifyCode);
        smsCodeSender.send(verifyCode.getCode(), String.valueOf(phoneNumber));

    }


    *//**
     * @param width     图形宽度
     * @param height    图形高度
     * @param strLength 验证码字符数
     * @return ImageCode对象
     *//*
    //private ImageCode generateImageCode(int width, int height, int strLength) {
    //    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    //    Graphics g = image.getGraphics();
    //    Random random = new Random();
    //
    //    g.setColor(getRandColor(200, 250));
    //    g.fillRect(0, 0, width, height);
    //    g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
    //    g.setColor(getRandColor(160, 200));
    //    for (int i = 0; i < 155; i++) {
    //        int x = random.nextInt(width);
    //        int y = random.nextInt(height);
    //        int xl = random.nextInt(12);
    //        int yl = random.nextInt(12);
    //        g.drawLine(x, y, x + xl, y + yl);
    //    }
    //
    //    String sRand = "";
    //    for (int i = 0; i < strLength; i++) {
    //        String rand = String.valueOf(random.nextInt(10));
    //        sRand += rand;
    //        g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
    //        g.drawString(rand, 13 * i + 6, 16);
    //    }
    //
    //    g.dispose();
    //
    //    return new ImageCode(sRand, image, 60);
    //}


    */
    /**
     * 生成随机背景条纹
     *
     * @param fc
     * @param bc
     * @return
     *//*
    //private Color getRandColor(int fc, int bc) {
    //    Random random = new Random();
    //    if (fc > 255) {
    //        fc = 255;
    //    }
    //    if (bc > 255) {
    //        bc = 255;
    //    }
    //    int r = fc + random.nextInt(bc - fc);
    //    int g = fc + random.nextInt(bc - fc);
    //    int b = fc + random.nextInt(bc - fc);
    //    return new Color(r, g, b);
    //}*/


    @Autowired
    private Map<String, VerifyCodeProcessor> verifyCodeProcessorMap = new HashMap<>();

    @GetMapping("/{type}")
    public void sendVerifyCode(@PathVariable String type, HttpServletRequest req, HttpServletResponse rsp) {
        if (!Objects.equals(type, IMAGE.getType()) && !Objects.equals(type, SMS.getType())) {
            throw new IllegalArgumentException("不支持的验证码类型");
        }
        VerifyCodeProcessor verifyCodeProcessor = verifyCodeProcessorMap.get(type + VERIFY_CODE_PROCESSOR_IMPL_SUFFIX);
        verifyCodeProcessor.sendVerifyCode(new ServletWebRequest(req, rsp));
    }
}
