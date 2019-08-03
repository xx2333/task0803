package com.lwy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lwy.constant.MessageConstant;
import com.lwy.entity.PageResult;
import com.lwy.entity.QueryPageBean;
import com.lwy.entity.Result;
import com.lwy.pojo.Menu;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
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
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return menuService.findPage(queryPageBean);
    }
    @RequestMapping("/findById")
    public Result findById(Integer id){
        Menu menu= menuService.findById(id);
        return new Result(true, MessageConstant.GET_MENU_SUCCESS, menu);
    }
    @RequestMapping("/add")
    public Result add(@RequestBody Menu menu){
        menuService.add(menu);
        return new Result(true, MessageConstant.ADD_MENU_SUCCESS);
    }
    @RequestMapping("/edit")
    public Result edit(@RequestBody Menu menu){
        menuService.edit(menu);
        return new Result(true, MessageConstant.EDIT_MENU_SUCCESS);
    }
    @RequestMapping("/findAll")
    public Result findAll(){
        List<Menu> list = menuService.findAll();
        return new Result(true,MessageConstant.GET_MENU_SUCCESS,list);
    }

    @RequestMapping("/delete")
    public Result delete(Integer id){
       menuService.deleteById(id);
        return new Result(true,MessageConstant.DELTE_MENU_SUCCESS);
    }

}
