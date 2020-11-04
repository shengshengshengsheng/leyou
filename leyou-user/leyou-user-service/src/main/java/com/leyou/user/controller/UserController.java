package com.leyou.user.controller;

import com.leyou.user.opjo.User;
import com.leyou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户数据的校验，主要包括对：手机号、用户名的唯一性校验。
     * @author XuQiangsheng
     * @date 2020/11/4 9:35
     * @param data 要校验的数据
     * @param type 要校验的数据类型：1，用户名；2，手机；
     * @return org.springframework.http.ResponseEntity<java.lang.Boolean>
    */
    @GetMapping("check/{data}/{type}")
    public ResponseEntity<Boolean> checkUserData(@PathVariable("data") String data, @PathVariable(value = "type") Integer type) {
        Boolean result = userService.checkUserData(data,type);
        if(result == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 发送手机验证码
     * @param phone
     * @return
     */
    @PostMapping("send")
    public ResponseEntity<Void> sendVerifyCode(String phone) {
        Boolean boo = this.userService.sendVerifyCode(phone);
        if (boo == null || !boo) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 注册新用户
     * @author XuQiangsheng
     * @date 2020/11/4 14:54
     * @param code 验证码
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
    */
    @PostMapping("register")
    public ResponseEntity<Void> register(@Valid User user,
                                         @RequestParam(value = "code")String code){
        Boolean boo = userService.register(user,code);
        if(boo == null || !boo){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    @GetMapping("query")
    public ResponseEntity<User> queryUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        User user = this.userService.queryUser(username, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(user);
    }
    
}