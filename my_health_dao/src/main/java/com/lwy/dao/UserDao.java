package com.lwy.dao;

import com.github.pagehelper.Page;
import com.lwy.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    User findByUsername(String username);
    List<User> findAll();
    Page<User> findByCondition(String queryString);
    void add(User user);

    void deleteById(Integer id);

    void edit(User user);

    void cleaRelationByUserId(Integer id);

    void serUserRoleRelation(@Param("uid") Integer uid, @Param("roleId")Integer roleId);

    User findById(Integer id);

    List<Integer> findRoleIdsById(Integer id);
}
