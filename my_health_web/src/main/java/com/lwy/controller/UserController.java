package com.lwy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lwy.constant.MessageConstant;
import com.lwy.entity.PageResult;
import com.lwy.entity.QueryPageBean;
import com.lwy.entity.Result;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@SuppressWarnings("all")
public class UserController {
    private final static Logger log = Logger.getLogger(UserController.class);
    @Reference
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
    @RequestMapping("/add")
    public Result add(@RequestBody com.lwy.pojo.User user, @RequestParam List<Integer> roleIds){
        String password = user.getPassword();
        if (password!=null&&password.length()>0) {
            password = bCryptPasswordEncoder.encode(password);
            user.setPassword(password);
        }
        userService.add(user,roleIds);

        return new Result(true, MessageConstant.ADD_ROLE_SUCCESS);
    }
    @RequestMapping("/edit")
    public Result edit(@RequestBody com.lwy.pojo.User user, @RequestParam List<Integer>  roleIds){
        String password = user.getPassword();
        if (password!=null&&password.length()>0) {
            password = bCryptPasswordEncoder.encode(password);
            user.setPassword(password);
        }
        userService.edit(user,roleIds);
        return new Result(true, MessageConstant.EDIT_ROLE_SUCCESS);
    }

    @RequestMapping("/delete")
    public Result deleteById(Integer id){
        userService.deleteById(id);
        return new Result(true, MessageConstant.Delete_ROLE_SUCCESS);
    }
    @RequestMapping("/findAll")
    public Result findAll(){
        List<com.lwy.pojo.User> list = userService.findAll();
        return new Result(true, MessageConstant.GET_ROLE_SUCCESS,list);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return userService.findPage(queryPageBean);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        com.lwy.pojo.User user = userService.findById(id);
        return new Result(true, MessageConstant.GET_USERINFO_SUCCESS,user);
    }
    @RequestMapping("/findRoleIdsById")
    public List<Integer> findRoleIdsById(Integer id){
        return userService.findRoleIdsById(id);
    }
}
