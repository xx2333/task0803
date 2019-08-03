package com.lwy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lwy.dao.MemberDao;
import com.lwy.dao.OrderDao;
import com.lwy.dao.SetmealDao;
import com.lwy.pojo.Setmeal;
import com.lwy.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import service.ReportService;

import java.text.SimpleDateFormat;
import java.util.*;

@Service(interfaceClass = ReportService.class)
public class ReportServiceImpl implements ReportService {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public List<Integer> getMemberCountsByMonths(List<String> months) {
        List<Integer> list = new ArrayList<>();
        if (months!=null&&months.size()>0){
            months.forEach(m->{
                m = m + ".31";
                list.add(memberDao.getMemberCountsBeforeDate(m));
            });
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getSetmealReport() {
        return setmealDao.getSetmealReport();
    }

    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {
        String today = DateUtils.parseDate2String(new Date());
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        String thisMonthFirstDay = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        Integer todayNewMember = memberDao.getMemberCountByDate(today);
        Integer totalMember =memberDao.getTotalCount();
        Integer thisWeekNewMember = memberDao.getMemberCountsAfterDate(thisWeekMonday);
        Integer thisMonthNewMember = memberDao.getMemberCountsAfterDate(thisMonthFirstDay);
        Integer todayOrderNumber =orderDao.getOrderNumberByDate(today);
        Integer todayVisitsNumber = orderDao.getVisitsNumberByDate(today);
        Integer thisWeekOrderNumber = orderDao.getOrderNumberAfterDate(thisWeekMonday);
        Integer thisWeekVisitsNumber =orderDao.getVisitsNumberAfterDate(thisWeekMonday);
        Integer thisMonthOrderNumber = orderDao.getOrderNumberAfterDate(thisMonthFirstDay);
        Integer thisMonthVisitsNumber = orderDao.getVisitsNumberAfterDate(thisMonthFirstDay);
        List<Map<String, Object>> hotSetmeal=setmealDao.getHotSetmeal();
        Map<String, Object> map = new HashMap<>();
        map.put("reportDate", today);
        map.put("todayNewMember",todayNewMember);
        map.put("totalMember",totalMember);
        map.put("thisWeekNewMember",thisWeekNewMember);
        map.put("thisMonthNewMember",thisMonthNewMember);
        map.put("todayOrderNumber",todayOrderNumber);
        map.put("todayVisitsNumber",todayVisitsNumber);
        map.put("thisWeekOrderNumber",thisWeekOrderNumber);
        map.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        map.put("thisMonthOrderNumber",thisMonthOrderNumber);
        map.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        map.put("hotSetmeal", hotSetmeal);
        return map;
    }
}
