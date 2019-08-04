package it.healthy.dao;

import it.healthy.domain.Member;

import java.util.Map;

public interface MemberDao {
    Member findMemberByTel(String telephone);

    void AutoAddMember(Member member);
}
