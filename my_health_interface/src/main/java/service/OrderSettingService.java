package service;

import com.lwy.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    void setNumbers(List<OrderSetting> orderSettings);

    List<Map<String, Object>> findByMonth(String date);

    void editNumberByDate(OrderSetting orderSetting);
}
