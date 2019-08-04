package it.healthy.service;

import com.alibaba.dubbo.config.annotation.Service;
import it.healthy.Utils.DateUtils;
import it.healthy.constant.LoginContanst;
import it.healthy.dao.MemberDao;
import it.healthy.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
@Service(interfaceClass = LoginService.class)
public class LoginServicelmp implements LoginService,Serializable {
    @Autowired
    private MemberDao memberDao;

    @Override
    public void autoLogin(Map<String,Object> map) {
        String telephone= (String) map.get("telephone");
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        Date date=null;
        try {
            date = DateUtils.parseString2Date(format);
        } catch (ParseException e) {
            throw new RuntimeException("there is a fail during string convert into date...........");
        }
        //首先查找会员是否存在,若不存在,则注册会员
        Member member=memberDao.findMemberByTel(telephone);
        if(member==null){
            member=new Member();
            member.setSex(LoginContanst.SEX_DEFAULT);
            member.setName(LoginContanst.ANONYMOUS);
            member.setRegTime(date);
            member.setPhoneNumber(telephone);
            memberDao.AutoAddMember(member);
        }
    }
}
