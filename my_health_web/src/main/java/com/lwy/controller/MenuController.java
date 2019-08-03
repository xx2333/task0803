package com.lwy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lwy.constant.MessageConstant;
import com.lwy.entity.Result;
import com.lwy.pojo.Menu;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.MenuService;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Reference
    private MenuService menuService;
    @RequestMapping("/getMenu")
    public Result getMenu(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Menu> list = menuService.findByUsername(user.getUsername());
        return new Result(true, MessageConstant.GET_MENU_SUCCESS,list);
    }
}
