package it.healthy.Utils;

import java.util.Random;

public class ValidateCodeUtils {
    public static Integer generateValidateCode(int length){
        Integer code=null;
        if(length==4){
            code=new Random().nextInt(9999);
            if(code<1000) code+=1000;
        }else if(length==6){
            code=new Random().nextInt(99999);
            if(code<1000) code+=10000;
        }else {
            throw new RuntimeException("只能生成4位或6位数字验证码");
        }
        return code;
    }

    public static String generateValidateCode4String(int length){
        Random rd=new Random();
        String s = Integer.toHexString(rd.nextInt());
        String substring = s.substring(0, length);
        return substring;
    }
}
