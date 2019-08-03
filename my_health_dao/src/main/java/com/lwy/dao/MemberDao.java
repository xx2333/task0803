package com.lwy.dao;

import com.lwy.pojo.Member;

import java.util.List;

public interface MemberDao {

    Member findByTelephone(String telephone);

    void add(Member member);


    Integer getMemberCountsBeforeDate(String date);

    Integer getMemberCountByDate(String today);

    Integer getTotalCount();

    Integer getMemberCountsAfterDate(String thisWeekMonday);
}
