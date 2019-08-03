package com.lwy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lwy.constant.MessageConstant;
import com.lwy.constant.RedisMessageConstant;
import com.lwy.entity.Result;
import com.lwy.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import service.OrderService;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map orderInfo) {
        try {
            String telephone = (String) orderInfo.get("telephone");


            String codeInDb = jedisPool.getResource().get(RedisMessageConstant.SENDTYPE_ORDER + telephone);
            if (codeInDb!=null&&!codeInDb.equals((String) orderInfo.get("validateCode"))) {
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }
            return orderService.order(orderInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "预约失败");
        }
    }
    @RequestMapping("/findById")
    public Result findById(Integer id){
        Map<String, Object> map = null;
        try {
            map = orderService.findDetailById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }

}
