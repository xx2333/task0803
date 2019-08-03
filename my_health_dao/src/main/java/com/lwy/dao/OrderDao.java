package com.lwy.dao;

import com.lwy.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {

    void add(Order order);

    List<Order> findByCondition(Order order);

    Map<String, Object> findDetailById(Integer id);

    Integer getOrderNumberByDate(String date);

    Integer getVisitsNumberByDate(String date);

    Integer getOrderNumberAfterDate(String date);

    Integer getVisitsNumberAfterDate(String date);
}
