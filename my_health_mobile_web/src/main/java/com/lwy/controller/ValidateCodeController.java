package com.lwy.controller;

import com.aliyuncs.exceptions.ClientException;
import com.lwy.constant.MessageConstant;
import com.lwy.constant.RedisConstant;
import com.lwy.constant.RedisMessageConstant;
import com.lwy.entity.Result;
import com.lwy.utils.SMSUtils;
import com.lwy.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
        try {
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,code);
            jedisPool.getResource().setex(RedisMessageConstant.SENDTYPE_ORDER+telephone, 60 * 5, code);
            System.out.println(code);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
    }
    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        try {
            String code = ValidateCodeUtils.generateValidateCode(6).toString();
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code);
            jedisPool.getResource().setex(RedisMessageConstant.SENDTYPE_LOGIN + telephone, 5 * 60, code);
            System.out.println(code);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

}
