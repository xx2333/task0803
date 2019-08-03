package com.lwy.controller;

import com.lwy.constant.MessageConstant;
import com.lwy.constant.RedisMessageConstant;
import com.lwy.entity.Result;
import com.lwy.pojo.Member;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;
import service.MemberService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Reference
    private MemberService memberService;
    @Autowired
    private JedisPool jedisPool;
    public Result check(@RequestBody Map map, HttpServletResponse response){
        String telephone = (String) map.get("telephone");
        String codeInDb = jedisPool.getResource().get(RedisMessageConstant.SENDTYPE_LOGIN + telephone);
        if (codeInDb==null||!codeInDb.equals((String)map.get("validateCode"))){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        Member member = memberService.findByTelephone(telephone);
        if (member==null) {
            member=new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberService.add(member);
        }
        Cookie cookie=new Cookie("userPhone",telephone);
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24*7);
        response.addCookie(cookie);
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }
}
