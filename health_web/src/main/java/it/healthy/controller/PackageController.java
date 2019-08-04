package it.healthy.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import it.healthy.Utils.QiniuUtils;
import it.healthy.constant.MessageConstant;
import it.healthy.constant.RedisConstant;
import it.healthy.domain.*;
import it.healthy.domain.Package;
import it.healthy.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/package")
public class PackageController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private PackageService packageService;
    @RequestMapping("/findAll")
    public Result findAll(){
        List<CheckGroup> list=packageService.findAll();
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
    }

    @PostMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        Result result =null;
        try {
            //获取初始文件名
            String originalFilename = imgFile.getOriginalFilename();
            //获取文件后缀名
            int lastIndexOf = originalFilename.lastIndexOf(".");
            String suffix=originalFilename.substring(lastIndexOf-1);
            //随机生成文件名称,防止同文件被覆盖
            String filename = UUID.randomUUID().toString() + suffix;

            QiniuUtils.upload2Qiniu(imgFile.getBytes(), filename);
            //将文件存储到redis中
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, filename);

            //4. 文件名返回给前端，在七牛中的domain(域名)也要一起返回给前端
            Map<String,String> imageMap = new HashMap<String,String>();
            imageMap.put("domain", QiniuUtils.DOMAIN);
            imageMap.put("image", filename);

            result = new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,imageMap);
        } catch(Exception e) {
            e.printStackTrace();
            result = new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return result;
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Package pacKage,Integer[] checkgroupIds){
        try {
            packageService.add(pacKage,checkgroupIds);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, pacKage.getImg());
        } catch(Exception e) {
            return new Result(false, MessageConstant.ADD_PACKAGE_FAIL);
        }
        return new Result(true, MessageConstant.ADD_PACKAGE_SUCCESS);
    }

    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Package> packagePageResult= packageService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_PACKAGE_SUCCESS,packagePageResult);
    }

    @RequestMapping("/getPackage")
    public Result getPackage(){
        List<Package> list=null;
        try {
           list= packageService.getPackage();
        } catch(Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_PACKAGE_FAIL);
        }
        return new Result(true,MessageConstant.QUERY_PACKAGE_SUCCESS,list);
    }

    @RequestMapping("/getPackageById")
    public Result getPackageById(Integer id){
        Package packageById=null;
        try {
             packageById=packageService.getPackageById(id);
        } catch(Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_PACKAGE_FAIL,packageById);
        }

        return new Result(true,MessageConstant.QUERY_PACKAGE_SUCCESS,packageById);
    }
}
