package com.lwy.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lwy.constant.MessageConstant;
import com.lwy.entity.PageResult;
import com.lwy.entity.QueryPageBean;
import com.lwy.entity.Result;
import com.lwy.pojo.Permission;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.PermissionService;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Reference
    private PermissionService permissionService;

    @RequestMapping("/findAll")
    public Result findAll(){
        List<Permission> list = permissionService.findAll();
        return new Result(true, MessageConstant.GET_PERMISSION_SUCCESS,list);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Permission permission){
        permissionService.add(permission);
        return new Result(true, MessageConstant.ADD_PERMISSION_SUCCESS);
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody Permission permission){
        permissionService.edit(permission);
        return new Result(true, MessageConstant.EDIT_PERMISSION_SUCCESS);
    }

    @RequestMapping("/delete")
    public Result deleteById(Integer id){
        permissionService.deleteById(id);
        return new Result(true, MessageConstant.EDIT_PERMISSION_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return permissionService.findPage(queryPageBean);
    }
}
