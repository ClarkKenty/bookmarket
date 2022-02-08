package com.darkcraft.bookmarket.controller;

import com.darkcraft.bookmarket.pojo.User;
import com.darkcraft.bookmarket.service.UserService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    /* 验证用户名是否存在 */
    @GetMapping(value = "/check/{userName}")
    @ResponseBody
    public Object checkUserName(
            @PathVariable("userName") String userName) {
        System.out.println(userName);
        return null;
    }

    @CrossOrigin
    @GetMapping(value = "/api/findAllUsers")
    @ResponseBody
    public Object findAllUsers() {
        return userService.findAll();
    }

    @CrossOrigin
    @PostMapping(value = "/api/updateUser")
    @ResponseBody
    public void updateUser(@RequestBody User user) {
        userService.addOrUpdate(user);
    }

    @CrossOrigin
    @GetMapping(value = "/api/deleteUser")
    @ResponseBody
    public void deleteUser( @RequestParam("id") int id) {
        userService.deleteById(id);
    }
}
