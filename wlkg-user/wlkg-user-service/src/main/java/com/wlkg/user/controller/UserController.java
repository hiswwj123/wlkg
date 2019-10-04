package com.wlkg.user.controller;

import com.wlkg.user.pojo.User;
import com.wlkg.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author:wangjun
 * @Data:Createa in 2019/9/28 0028 14:18
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/check/{data}/{type}")
    public Boolean checkUserExist(@PathVariable("data") String data, @PathVariable(value = "type", required = false) Integer type) {

        Boolean rest = userService.checkUserExist(data, type);
        return rest;
    }

    @PostMapping("/code")
    public ResponseEntity<Void> sendVerifyCode(String phone) {
        Boolean boo = userService.sendVerifyCode(phone);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid User user, @RequestParam("code") String code) {
        userService.register(user, code);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("query")
    public ResponseEntity<User> queryUser(@RequestParam("username") String username,
                                          @RequestParam("password") String password) {

        User user = userService.queryUser(username, password);
        return ResponseEntity.ok(user);
    }

    @GetMapping("query/{username}/{password}/{type}")
    public ResponseEntity<User> query(@PathVariable("username") String username,
                                            @PathVariable("password") String password,
                                            @PathVariable("type") Integer type){
        User user = userService.login(username, password, type);
        return ResponseEntity.ok(user);
    }

}
