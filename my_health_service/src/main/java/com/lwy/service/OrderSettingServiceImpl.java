package com.lwy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lwy.dao.OrderSettingDao;
import com.lwy.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import service.OrderSettingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Override
    public void setNumbers(List<OrderSetting> orderSettings) {
        if (orderSettings !=null) {
            for (OrderSetting orderSetting : orderSettings) {
              editNumberByDate(orderSetting);
            }
        }
    }

    @Override
    public List<Map<String, Object>> findByMonth(String date) {
        String first = date + "-1";
        String end = date + "-31";
        List<OrderSetting> list = orderSettingDao.findOrderSettingBetweennDate(first, end);
        List<Map<String, Object>> maps = new ArrayList<>();
        if (list !=null&&list.size()>0) {
            for (OrderSetting orderSetting : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("date", orderSetting.getOrderDate().getDate());
                map.put("number", orderSetting.getNumber());
                map.put("reservations",orderSetting.getReservations());
                maps.add(map);
            }
        }
        return maps;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        OrderSetting orderSetting1=   orderSettingDao.findByDate(orderSetting.getOrderDate());
        if (orderSetting1==null) {
            orderSettingDao.add(orderSetting);
        }else {
            orderSettingDao.updateNumberByDate(orderSetting);
        }
    }
}
