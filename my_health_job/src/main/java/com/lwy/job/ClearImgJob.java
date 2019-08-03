package com.lwy.job;

import com.lwy.constant.RedisConstant;
import com.lwy.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;

    public void ClearImg(){
        System.out.println("定时任务调用了");
        Set<String> sdiff = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if (sdiff!=null) {
            for (String s : sdiff) {
                QiniuUtils.deleteFileFromQiniu(s);
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, s);
                System.out.println("已移除垃圾图片："+s);
            }
        }


    }

}
