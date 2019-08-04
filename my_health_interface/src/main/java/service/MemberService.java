package service;

import com.lwy.entity.PageResult;
import com.lwy.entity.QueryPageBean;
import com.lwy.pojo.Member;

public interface MemberService {
    Member findByTelephone(String telephone);

    void add(Member member);

    PageResult findPage(QueryPageBean queryPageBean);
}
