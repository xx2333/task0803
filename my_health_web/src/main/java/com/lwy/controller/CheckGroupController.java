package com.lwy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lwy.constant.MessageConstant;
import com.lwy.entity.PageResult;
import com.lwy.entity.QueryPageBean;
import com.lwy.entity.Result;
import com.lwy.pojo.CheckGroup;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.CheckGroupService;

import java.util.List;

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;

    @PreAuthorize("hasAuthority('CHECKGROUP_QUERY')")//权限校验
    @RequestMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return checkGroupService.findPage(queryPageBean);
    }
    @PreAuthorize("hasAuthority('CHECKGROUP_ADD')")
    @RequestMapping("/add.do")
    public Result add(@RequestBody CheckGroup checkGroup, @RequestParam(required = false) List<Integer> checkitemIds) {
        try {
            checkGroupService.add(checkGroup,checkitemIds);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);

        }
    }
    @PreAuthorize("hasAuthority('CHECKGROUP_EDIT')")
    @RequestMapping("/edit.do")
    public Result edit(@RequestBody CheckGroup checkGroup, @RequestParam(required = false) List<Integer> checkitemIds) {
        try {
            checkGroupService.edit(checkGroup, checkitemIds);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);

        }
    }
    @PreAuthorize("hasAuthority('CHECKGROUP_QUERY')")
    @RequestMapping("/findById.do")
    public Result findById(Integer id){
       CheckGroup checkGroup=checkGroupService.findById(id);
        if (checkGroup!=null) {
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
        }else {
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('CHECKGROUP_QUERY')")
    @RequestMapping("/findCheckitemIdsById.do")
    public List<Integer> findCheckitemIdsById(Integer id){
        return checkGroupService.findCheckitemIdsById(id);
    }

    @PreAuthorize("hasAuthority('CHECKGROUP_QUERY')")
    @RequestMapping("/findAll.do")
    public Result findAll(){
        try {
            List<CheckGroup> list=checkGroupService.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
}
