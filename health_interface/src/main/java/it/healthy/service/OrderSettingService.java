package it.healthy.service;

import it.healthy.domain.OrderSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    void add(ArrayList<OrderSetting> objects);

    List<Map> getOrderSettingByMonth(String date);

    void editNumberByDate(String orderDate, int number);
}
