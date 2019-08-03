package com.lwy.controller;

import com.lwy.constant.MessageConstant;
import com.lwy.entity.Result;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final static Logger log = Logger.getLogger(UserController.class);
    @RequestMapping("/getUsername")
    public Result getUsername(){
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
        } catch (Exception e) {
            log.error("errorMsg:"+e);

            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }
}
