package com.lwy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lwy.dao.MemberDao;
import com.lwy.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import service.MemberService;
@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;
    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        memberDao.add(member);
    }
}
