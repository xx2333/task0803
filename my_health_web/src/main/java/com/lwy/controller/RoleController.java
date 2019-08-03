package com.lwy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lwy.constant.MessageConstant;
import com.lwy.entity.Result;
import com.lwy.pojo.Role;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Reference
    private RoleService roleService;

    @RequestMapping("/add")
    public Result add(@RequestBody Role role, @RequestParam List<Integer> permissionIds,@RequestParam  List<Integer> menuIds){
        roleService.add(role,permissionIds,menuIds);

        return new Result(true, MessageConstant.ADD_ROLE_SUCCESS);
    }
    @RequestMapping("/edit")
    public Result edit(@RequestBody Role role, @RequestParam List<Integer> permissionIds,@RequestParam  List<Integer> menuIds){
        roleService.edit(role,permissionIds,menuIds);
        return new Result(true, MessageConstant.EDIT_ROLE_SUCCESS);
    }

    @RequestMapping("/delete")
    public Result deleteById(Integer id){
        roleService.deleteById(id);
        return new Result(true, MessageConstant.Delete_ROLE_SUCCESS);
    }
    @RequestMapping("/findAll")
    public Result findAll(){
        List<Role> list = roleService.findAll();
        return new Result(true, MessageConstant.GET_ROLE_SUCCESS,list);
    }

}
