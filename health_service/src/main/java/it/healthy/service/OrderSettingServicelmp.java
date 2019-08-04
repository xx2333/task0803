package it.healthy.service;

import com.alibaba.dubbo.config.annotation.Service;
import it.healthy.dao.OrderSettingDao;
import it.healthy.domain.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(interfaceClass=OrderSettingService.class)
public class OrderSettingServicelmp implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Override
    public void add(ArrayList<OrderSetting> list) {
        if(list!=null&&list.size()>0){
            for (OrderSetting orderSetting : list) {
                long count=orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if(count>0){
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }else {
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        String[] split = date.split("-");
        Integer year=Integer.parseInt(split[0]);
        Integer month=Integer.parseInt(split[1]);
        List<OrderSetting> list=orderSettingDao.getOrderSettingByYearAndMonth(year,month);
        List<Map> data = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map orderSettingMap = new HashMap();
            orderSettingMap.put("date",orderSetting.getOrderDate().getDate());//获得日期（几号）
            orderSettingMap.put("number",orderSetting.getNumber());//可预约人数
            orderSettingMap.put("reservations",orderSetting.getReservations());//已预约人数
            data.add(orderSettingMap);
        }
        return data;
    }

    @Override
    public void editNumberByDate( String orderDate, int number) {
        OrderSetting orderSetting=new OrderSetting();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        orderSetting.setNumber(number);
        orderSetting.setReservations(0);
        try {
            orderSetting.setOrder(simpleDateFormat.parse(orderDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long countByOrderDate = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if(countByOrderDate>0){
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else {
            orderSettingDao.add(orderSetting);
        }
    }
}
