package com.lwy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lwy.constant.MessageConstant;
import com.lwy.constant.RedisConstant;
import com.lwy.entity.PageResult;
import com.lwy.entity.QueryPageBean;
import com.lwy.entity.Result;
import com.lwy.pojo.OrderSetting;
import com.lwy.pojo.Setmeal;
import com.lwy.utils.QiniuUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.UUIDEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;
import service.SetmealService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetMealController {
    @Reference
    private SetmealService setmealService;
    @Autowired
    private JedisPool jedisPool;
    @PreAuthorize("hasAuthority('SETMEAL_ADD')")
    @RequestMapping("/add.do")
    public Result add(@RequestParam List<Integer> checkgroupIds, @RequestBody Setmeal setmeal) {
        try {
            setmealService.add(setmeal, checkgroupIds);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('SETMEAL_ADD')")
    @RequestMapping("/upload.do")
    public Result upload(MultipartFile imgFile)  {
        try {
            String filename = imgFile.getOriginalFilename();
            System.out.println(filename);
            int i = filename.lastIndexOf(".");
            String substring = filename.substring( i - 1);//后缀
            filename= UUID.randomUUID().toString()+substring;

            QiniuUtils.upload2Qiniu(imgFile.getBytes(),filename);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, filename);
            return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,filename);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }

    }
    @PreAuthorize("hasAuthority('SETMEAL_QUERY')")
    @RequestMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return setmealService.findPage(queryPageBean);
    }


}
