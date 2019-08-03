package com.lwy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lwy.constant.MessageConstant;
import com.lwy.entity.Result;
import com.lwy.pojo.Setmeal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.SetmealService;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;
    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
        List<Setmeal> list=setmealService.findAll();
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,list);
    }
    @RequestMapping("/findById")
    public Result findById(Integer id){
        Setmeal setmeal=setmealService.findById(id);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
    }
}
