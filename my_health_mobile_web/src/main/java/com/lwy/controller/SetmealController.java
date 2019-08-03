package com.lwy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lwy.constant.MessageConstant;
import com.lwy.entity.Result;
import com.lwy.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;
import service.SetmealService;
import com.lwy.utils.fastJsonUtil;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;


    @RequestMapping("/getSetmeal")
    public Result getSetmeal() {

        List<Setmeal> setmealList = null;
        // 从redis 中拿到setmeal集合的json字符串
        String setmealList_json = jedisPool.getResource().get("setmealList_json");
        // 判断
        if (setmealList_json == null || "".equals(setmealList_json)){
            setmealList = setmealService.findAll();
            //转为json字符串
            setmealList_json = fastJsonUtil.toJsonString(setmealList);
            jedisPool.getResource().set("setmealList_json", setmealList_json);
        }

        // 转为list对象
        setmealList = fastJsonUtil.toList(setmealList_json, Setmeal.class);

        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmealList);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id) {

        Setmeal setmeal = null;
        String setmeal_json = jedisPool.getResource().get("setmeal_json_" + id);

        if (setmeal_json == null || "".equals(setmeal_json)){
            setmeal = setmealService.findById(id);
            setmeal_json = fastJsonUtil.toJsonString(setmeal);
            jedisPool.getResource().set("setmeal_json_" + id, setmeal_json);
        }

        setmeal = fastJsonUtil.toObject(setmeal_json,Setmeal.class);

        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
    }
}