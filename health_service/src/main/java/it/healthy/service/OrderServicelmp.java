package it.healthy.service;

import com.alibaba.dubbo.config.annotation.Service;
import it.healthy.Utils.DateUtils;
import it.healthy.constant.OrderContanst;
import it.healthy.dao.MemberDao;
import it.healthy.dao.OrderDao;
import it.healthy.dao.OrderSettingDao;
import it.healthy.domain.Member;
import it.healthy.domain.Order;
import it.healthy.domain.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.Map;
@Service(interfaceClass = OrderService.class)
public class OrderServicelmp implements OrderService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Override
    @Transactional
    public Integer orderService(Map map) throws Exception{
        String  orderDate = (String) map.get("orderDate");
        String  telephone = (String) map.get("telephone");
        Date date = DateUtils.parseString2Date(orderDate);
       //1.查看该日期是否可以进行预约
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(date);
        if(orderSetting==null) return -1;
        //2.查看该日期是否已经预约满员
        long total=orderSetting.getNumber();
        long reservations=orderSetting.getReservations();
        if(total-reservations<=0) return 0;

        //3.查看用户是否是会员
        Member member=memberDao.findMemberByTel(telephone);
        //3.1 .非会员用户,先进行会员操作
        if(member==null){
            member=new Member();
            member.setName((String) map.get("name"));
            member.setSex((String) map.get("sex"));
            member.setIdCard((String) map.get("idCard"));
            member.setPhoneNumber(telephone);
            member.setRegTime(date);
            memberDao.AutoAddMember(member);
        }
        //3.2 查看会员用户是否在同一天进行了重复预约
        Order init_order=new Order();
        init_order.setMember_id(member.getId());
        init_order.setOrderDate(date);
        init_order.setPackage_id(Integer.parseInt((String) map.get("setmealId")));
        init_order.setOrderStatus(OrderContanst.ORDERSRATUS_INIT);
        Order order=orderDao.findOrder(init_order);
        // 同一天重复预约
        if(order!=null) return -2;
        //4. 增加预约项
        order=new Order();
        order.setMember_id(member.getId());
        order.setOrderDate(date);
        order.setOrderType("微信预约");
        order.setOrderStatus("未就诊");
        order.setPackage_id(Integer.parseInt((String) map.get("setmealId")));
        orderDao.addOrder(order);
        //更新预约人数
        orderSettingDao.updateOrder(date);
        return order.getId();
    }

    @Override
    public Order findOrderById(Integer id) {
        return orderDao.findOrderById(id);

    }
}
