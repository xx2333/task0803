package com.lwy.dao;

import com.lwy.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderSettingDao {
    OrderSetting findByDate(Date orderDate);

    void add(OrderSetting orderSetting);

    void updateNumberByDate(OrderSetting orderSetting);

    List<OrderSetting> findOrderSettingBetweennDate(@Param("first") String first, @Param("end") String end);

    void updateReservationByDate(Date orderDate);

    void clearHistory(Date time);
}
