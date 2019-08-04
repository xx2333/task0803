package com.lwy.dao;

import com.github.pagehelper.Page;
import com.lwy.pojo.Member;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MemberDao {

    Member findByTelephone(String telephone);

    void add(Member member);


    Integer getMemberCountsBeforeDate(String date);

    Integer getMemberCountByDate(String today);

    Integer getTotalCount();

    Integer getMemberCountsAfterDate(String thisWeekMonday);

    Page<Member> findByCondition(String queryString);

    List<Map<String, Object>> getMemberByGender();

    int getMemberByAge(@Param("start") Date start, @Param("end") Date end);
}
