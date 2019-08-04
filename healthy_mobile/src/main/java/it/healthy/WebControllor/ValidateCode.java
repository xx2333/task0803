package it.healthy.WebControllor;

import com.mysql.jdbc.log.Log;
import it.healthy.Utils.SMSUtils;
import it.healthy.Utils.ValidateCodeUtils;
import it.healthy.constant.MessageConstant;
import it.healthy.constant.RedisConstant;
import it.healthy.domain.Result;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCode {
    @Autowired
    private JedisPool jedisPool;
    @RequestMapping("/send4Order")
    public Result send4Order(@RequestParam("telephone") String telephone){
        String s = ValidateCodeUtils.generateValidateCode4String(6);
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,s);
        } catch(Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_MESSAGE_FAIL);
        }
        System.out.println("发送验证码为:" + s);
        jedisPool.getResource().setex(telephone+ RedisConstant.SENDTYPE_ORDER, 60*60*24, s);
        return new Result(true, MessageConstant.SEND_MESSAGE_SUCCESS);
    }
}
