package it.healthy.WebControllor;

import com.alibaba.dubbo.config.annotation.Reference;
import it.healthy.constant.LoginContanst;
import it.healthy.constant.RedisConstant;
import it.healthy.domain.Result;
import it.healthy.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private LoginService loginService;
    @RequestMapping("/autoLogin")
    public Result autoLogin(@RequestBody Map<String,Object> map){
        String telephone= (String) map.get("telephone");
        String validateCode= (String) map.get("validateCode");
        String code = jedisPool.getResource().get(telephone + RedisConstant.SENDTYPE_ORDER);
        //1.判断验证码是否正确
        if(code==null||!code.equals(validateCode)){
            return new Result(false, LoginContanst.VALITADE_ERROR);
        }
        try {
            loginService.autoLogin(map);
        } catch(Exception e) {
            e.printStackTrace();
            return new Result(false,LoginContanst.LOGIN_FAIL);
        }
        return new Result(true,LoginContanst.LOGIN_SUCCESS);
    }
}
