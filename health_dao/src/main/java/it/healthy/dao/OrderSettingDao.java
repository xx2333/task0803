package it.healthy.dao;

import it.healthy.domain.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderSettingDao {
    void add(OrderSetting orderSetting);

    long findCountByOrderDate(Date order);

    void editNumberByOrderDate(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByYearAndMonth(@Param("year") Integer year,@Param("month") Integer month);

    OrderSetting findByOrderDate(Date date);

    void updateOrder(Date date);
}
