package it.healthy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import it.healthy.constant.MessageConstant;
import it.healthy.domain.CheckItem;
import it.healthy.domain.PageResult;
import it.healthy.domain.QueryPageBean;
import it.healthy.domain.Result;
import it.healthy.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.Serializable;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController implements Serializable {
    @Reference
    private CheckItemService checkItemServicelmp;

    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        System.out.println(checkItem);
        checkItemServicelmp.add(checkItem);
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<CheckItem> pageResult=checkItemServicelmp.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);
    }

    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            checkItemServicelmp.delete(id);
        } catch (RuntimeException e1){
            return new Result(false,e1.getMessage());
        } catch(Exception e) {
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        CheckItem checkItem=null;
        try {
            checkItem=checkItemServicelmp.findById(id);
        } catch(Exception e) {
            return new Result(true,MessageConstant.QUERY_CHECKITEM_FAIL,checkItem);
        }
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }

    @RequestMapping("/update")
    public Result update(CheckItem checkItem){
        try {
            checkItemServicelmp.update(checkItem);
        } catch(Exception e) {
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }
}
