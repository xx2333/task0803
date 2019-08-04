
import it.healthy.Utils.SMSUtils;
import it.healthy.Utils.ValidateCodeUtils;
import it.healthy.constant.MessageConstant;
import it.healthy.constant.RedisConstant;
import it.healthy.domain.Result;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import redis.clients.jedis.JedisPool;

public class Mytest {

    @Autowired
    private JedisPool jedisPool;
    @Test
    public void send4Order(){
        String s = ValidateCodeUtils.generateValidateCode4String(6);
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,"18339543638",s);
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("发送验证码为:" + s);
    }
}
