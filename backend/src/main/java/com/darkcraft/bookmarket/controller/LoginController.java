package com.darkcraft.bookmarket.controller;

import com.darkcraft.bookmarket.pojo.User;
import com.darkcraft.bookmarket.result.Result;
import com.darkcraft.bookmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.util.Objects;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping(value = "api/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser) {
        // 对 html 标签进行转义，防止 XSS 攻击
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);

        User user = userService.get(username, requestUser.getPassword());
        if (null == user) {
            String message = "账号密码错误";
            System.out.println("test");
            return new Result(400);
        } else {
            return new Result(200);
        }
    }

    @CrossOrigin
    @PostMapping(value = "api/signup")
    @ResponseBody
    public Result signup(@RequestBody User requestUser) {
        // 对 html 标签进行转义，防止 XSS 攻击
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);
        requestUser.setUsername(username);
        if(userService.isExist(username)){
            return new Result(600);
        }
        try{
            userService.add(requestUser);
            return new Result(200);
        }
        catch (Exception e){
            return new Result(400);
        }
    }
}

