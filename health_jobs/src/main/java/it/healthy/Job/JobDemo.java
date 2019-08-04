package it.healthy.Job;

import it.healthy.Utils.QiniuUtils;
import it.healthy.constant.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class JobDemo{
    @Autowired
    private JedisPool jedisPool;

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext-jobs.xml");
    }

    public void run(){
        System.out.println("hello world...");
    }

    public void clearImg(){
        System.out.println("clearImg has been used .....");
        Set<String > set=jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        for (String pic:set){
            QiniuUtils.delete(pic);
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, pic);
        }
    }
}
