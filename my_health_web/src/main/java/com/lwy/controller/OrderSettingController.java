package com.lwy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lwy.constant.MessageConstant;
import com.lwy.entity.Result;
import com.lwy.pojo.OrderSetting;
import com.lwy.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import service.OrderSettingService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    @PreAuthorize("hasAuthority('ORDERSETTING')")
    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile){
        try {
            List<String[]> strings = POIUtils.readExcel(excelFile);
            if (strings!=null) {
                List<OrderSetting> orderSettings = new ArrayList<>();
                for (String[] string : strings) {
                    orderSettings.add(new OrderSetting(new Date(string[0]), Integer.parseInt(string[1])));
                }
                orderSettingService.setNumbers(orderSettings);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(true, MessageConstant.UPLOAD_SUCCESS);
    }
    @RequestMapping("/findByMonth")
    public Result findByMonth(String date){
        try {
            List<Map<String, Object>> list = orderSettingService.findByMonth(date);
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
    }
    @PreAuthorize("hasAuthority('ORDERSETTING')")
    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {
        orderSettingService.editNumberByDate(orderSetting);
        return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
    }

}
