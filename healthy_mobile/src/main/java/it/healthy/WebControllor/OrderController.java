package it.healthy.WebControllor;

import com.alibaba.dubbo.config.annotation.Reference;
import it.healthy.Utils.SMSUtils;
import it.healthy.constant.MessageConstant;
import it.healthy.constant.OrderContanst;
import it.healthy.constant.RedisConstant;
import it.healthy.domain.Order;
import it.healthy.domain.Result;
import it.healthy.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    OrderService orderService;
    @Autowired
    private JedisPool jedisPool;
    @RequestMapping("/submit")
    public Result Submit(@RequestBody Map map){
        String telephone = (String) map.get("telephone");
        String code = jedisPool.getResource().get(telephone + RedisConstant.SENDTYPE_ORDER);
        String  validateCode = (String) map.get("validateCode");
        if(code == null || !code.equals(validateCode)){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        Integer id=null;
        try {
            map.put("orderType", OrderContanst.ORDERTYPE_WEIXIN);
            id=orderService.orderService(map);
        } catch(Exception e) {
            return new Result(false,OrderContanst.ORDER_FAIL);
        }
        //日期不可进行预约
        if(id==-1) return new Result(false,OrderContanst.ORDER_ERROR);
        //预约人数已满
        if(id==0) return new Result(false,OrderContanst.ORDER_PEOPLE_OVERFLOW);

        //重复预约
        if(id==-2) return new Result(false,OrderContanst.ORDER_REPEAT);

        String orderDate = (String) map.get("orderDate");
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,orderDate);
        } catch(Exception e) {
            e.printStackTrace();
        }
        Order order=new Order();
        order.setId(id);
        return new Result(true,OrderContanst.ORDER_SUCCESS,order);

    }

    @RequestMapping("/findOrderById")
    public Result findOrderById(Integer id){
        Order order=orderService.findOrderById(id);
        return new Result(true,OrderContanst.QUERY_SUCCESS,order);
    }

}
