package com.baizhi.controller;

import com.baizhi.utils.CaptchaCodeUtil;
import org.springframework.http.HttpRequest;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping("getImage")
    public String getImage(HttpServletRequest request) throws IOException {
        //1.使用工具类生成验证码
        String code = CaptchaCodeUtil.generateVerifyCode(4);
        //2.将验证码放入servletContext
        request.getServletContext().setAttribute("code",code);
        //将验证码图片转为base64
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        CaptchaCodeUtil.outputImage(120,30,byteArrayOutputStream,code);
        String s ="data:image/png;base64," + Base64Utils.encodeToString(byteArrayOutputStream.toByteArray());
        return s;
    }
}
