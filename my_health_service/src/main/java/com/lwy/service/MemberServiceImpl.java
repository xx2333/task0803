package com.lwy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lwy.dao.MemberDao;
import com.lwy.entity.PageResult;
import com.lwy.entity.QueryPageBean;
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

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        String queryString = queryPageBean.getQueryString();
        if (queryString!=null){
            queryString="%"+queryString+"%";
            queryPageBean.setCurrentPage(1);
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Member> page= memberDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }
}
