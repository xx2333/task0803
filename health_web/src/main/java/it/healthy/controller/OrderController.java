package it.healthy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sun.org.apache.xpath.internal.operations.Or;
import it.healthy.Utils.POIUtils;
import it.healthy.constant.MessageConstant;
import it.healthy.domain.OrderSetting;
import it.healthy.domain.Result;
import it.healthy.service.OrderSettingService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderController {
    @Reference
    private OrderSettingService orderSettingService;
    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile){
        try {
            List<String[]> strings = POIUtils.readExcel(excelFile);
            if(strings!=null&&strings.size()>0){
                ArrayList<OrderSetting> objects = new ArrayList<>();
                for (String[] string : strings) {
                    OrderSetting orderSetting = new OrderSetting(new Date(string[0]), Integer.parseInt(string[1]));
                    objects.add(orderSetting);
                }
                orderSettingService.add(objects);
            }
        } catch(Exception e) {
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){//参数格式为：2019-03
        List<Map> list=null;
        try {
            list=orderSettingService.getOrderSettingByMonth(date);

        } catch(Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
        return new Result(true,MessageConstant.GET_ORDERSETTING_SUCESS,list);
    }
    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody Map<String,String> orderDate){
        try {
            orderSettingService.editNumberByDate(orderDate.get("orderDate"),Integer.parseInt(orderDate.get("number")));
        } catch(Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);

    }
}
