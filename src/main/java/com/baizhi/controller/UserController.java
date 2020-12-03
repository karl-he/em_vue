package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.utils.CaptchaCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.Base64Utils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    //生成图形验证码
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

    //注册
    @PostMapping("register")
    public Map<String,Object> register(@RequestBody User user, String code, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        try{
            String verifyCode = (String) request.getServletContext().getAttribute("code");
            if(code.equalsIgnoreCase(verifyCode)){
                userService.register(user);
                map.put("status",true);
                map.put("msg","注册成功");
            }else{
                throw new RuntimeException("验证码输入错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("status",false);
            map.put("msg","提示："+e.getMessage());
        }
        return map;
    }

    //登陆
    @PostMapping("login")
    public Map<String,Object> login(@RequestBody User user){
        System.out.printf("come in....");
        Map<String,Object> map = new HashMap<>();
        User loginUser = userService.login(user);
        if(ObjectUtils.isEmpty(loginUser)){
            map.put("status",false);
            map.put("msg","用户名或密码不正确");
        }else{
            map.put("msg","登陆成功");
        }
        return map;
    }
}
