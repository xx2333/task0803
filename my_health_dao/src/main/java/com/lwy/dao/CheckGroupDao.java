package com.lwy.dao;

import com.github.pagehelper.Page;
import com.lwy.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupDao {
    Page<CheckGroup> selectByCondition(String queryString);

    void add(CheckGroup checkGroup);

    void setCheckGroupAndCheckItemRelation(@Param("checkgroupId") Integer id, @Param("checkitemId") Integer checkitemId);

    void edit(CheckGroup checkGroup);

    void clearCheckGroupAndCheckItemRelation(Integer id);

    CheckGroup findById(Integer id);

    List<Integer> findCheckitemIdsById(Integer id);

    List<CheckGroup> findAll();
    List<CheckGroup>  findBySetmealId(Integer id);
}
