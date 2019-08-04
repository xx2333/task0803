package it.healthy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import it.healthy.constant.MessageConstant;
import it.healthy.domain.*;
import it.healthy.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController implements Serializable {
    @Reference
    private CheckGroupService checkGroupService;
    @RequestMapping("/findAll")
    public Result findAll(){
        List<CheckItem> list= checkGroupService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try {
            checkGroupService.add(checkGroup,checkitemIds);
        } catch(Exception e) {
            return new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCESS);
    }
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<CheckGroup> byCondition = checkGroupService.findByCondition(queryPageBean);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,byCondition);
    }
    @RequestMapping("/findById")
    public Result findById(Integer id){
        CheckGroup checkGroup=null;
        try {
            checkGroup=checkGroupService.findById(id);
        } catch(Exception e) {
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id){
        List<Integer> list =new LinkedList<>();
        try {
            list=checkGroupService.findCheckItemIdsByCheckGroupId(id);
        } catch(Exception e) {
            return new Result(false,MessageConstant.ADD_findCheckItemIdsByCheckGroupId_FAIL);
        }
        return new Result(true,MessageConstant.ADD_findCheckItemIdsByCheckGroupId_SUCESS,list);
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try {
            checkGroupService.edit(checkGroup,checkitemIds);
        } catch(Exception e) {
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            checkGroupService.delete(id);
        } catch(Exception e) {
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }
}
