package com.lwy.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lwy.dao.OrderSettingDao;
import com.lwy.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import service.OrderSettingService;

import java.util.Calendar;
import java.util.Date;

public class ClearOrderSettingJob {
    @Autowired
    private OrderSettingDao orderSettingDao;


    public void clearOrderSetting(){

        System.out.println("清楚历史预约数据开始了");
        Calendar calendar=Calendar.getInstance();
        Date firstDayOfThisMonth = DateUtils.getFirstDay4ThisMonth();
        calendar.setTime(firstDayOfThisMonth);
        calendar.add(Calendar.MONTH,-3);
        Date time = calendar.getTime();
        orderSettingDao.clearHistory(time);
        System.out.println("清楚历史记录成功");

    }
}
