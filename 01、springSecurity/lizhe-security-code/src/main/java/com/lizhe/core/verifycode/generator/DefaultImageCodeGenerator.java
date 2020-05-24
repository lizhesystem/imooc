package com.lizhe.core.verifycode.generator;

import com.lizhe.core.properties.SecurityProperties;
import com.lizhe.core.verifycode.po.ImageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 默认的生成图形验证码的实现，方法作为该接口的默认实现
 *
 * @author lz
 * @create 2020-05-16
 */
public class DefaultImageCodeGenerator implements VerifyCodeGenerator<ImageCode> {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    HttpServletRequest request;
    //@Override
    //public ImageCode generateImageCode(int width, int height, int strLength, int durationSeconds) {
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
    //    return new ImageCode(sRand, image, durationSeconds);
    //
    //}


    @Override
    public ImageCode generateVerifyCode() {
        int width = ServletRequestUtils.getIntParameter(request, "width", securityProperties.getCode().getImage().getWidth());
        int height = ServletRequestUtils.getIntParameter(request, "height", securityProperties.getCode().getImage().getHeight());
        int strLength = securityProperties.getCode().getImage().getStrLength();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Random random = new Random();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        String sRand = "";
        for (int i = 0; i < strLength; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }

        g.dispose();

        return new ImageCode(sRand, image, securityProperties.getCode().getImage().getDurationSeconds());

    }


    /**
     * 生成随机背景条纹
     *
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
