package com.lwy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lwy.constant.MessageConstant;
import com.lwy.dao.MemberDao;
import com.lwy.dao.OrderDao;
import com.lwy.dao.OrderSettingDao;
import com.lwy.entity.Result;
import com.lwy.pojo.Member;
import com.lwy.pojo.Order;
import com.lwy.pojo.OrderSetting;
import com.lwy.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import service.OrderService;
import service.OrderSettingService;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    MemberDao memberDao;
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    @Transactional
    public Result order(Map orderInfo) throws Exception {
        String telephone = (String) orderInfo.get("telephone");
        Date orderDate = DateUtils.parseString2Date((String) orderInfo.get("orderDate"));
        OrderSetting orderSetting = orderSettingDao.findByDate(orderDate);
        //所选日期能否预约
        if (orderSetting==null){
            //不能预约
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //所选日期预约人数是否已满
        if (orderSetting.getNumber()<=orderSetting.getReservations()) {
            //已满
            return new Result(false,MessageConstant.ORDER_FULL);
        }
        Member member=memberDao.findByTelephone(telephone);
        Order order=new Order();
        order.setOrderDate(orderDate);
        order.setSetmealId(Integer.parseInt((String)orderInfo.get("setmealId")));
        //用户是否已是会员
        if (member!=null) {
            //是会员
            order.setMemberId(member.getId());
            List<Order> list=  orderDao.findByCondition(order);
            //是否已经预约过当日的该套餐
            if (list!=null&&list.size()>0){
                //重复预约
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        }else {
            //不是会员
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            member.setSex((String) orderInfo.get("sex"));
            member.setSex((String) orderInfo.get("idCard"));
            member.setSex((String) orderInfo.get("name"));
            memberDao.add(member);
            order.setMemberId(member.getId());
        }
        order.setOrderType("微信预约");
        order.setOrderStatus("未到诊");
        orderDao.add(order);
        orderSettingDao.updateReservationByDate(orderDate);
        System.out.println(order.getId());
        return new Result(true, MessageConstant.ORDER_SUCCESS,order.getId().toString());
    }

    @Override
    public Map<String, Object> findDetailById(Integer id) throws Exception {
        Map<String, Object> map = orderDao.findDetailById(id);
        Date orderDate = (Date) map.get("orderDate");
        map.put("orderDate", DateUtils.parseDate2String(orderDate));
        return map;
    }
}
