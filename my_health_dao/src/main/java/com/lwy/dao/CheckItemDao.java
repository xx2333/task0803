package com.lwy.dao;

import com.github.pagehelper.Page;
import com.lwy.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {
    void add(CheckItem checkItem);

    Page<CheckItem> selectByCondition(String queryString);

    void edit(CheckItem checkItem);

    long findCountByCheckItemId(Integer id);

    void deleteById(Integer id);

    List<CheckItem> findAll();
    List<CheckItem> findByCheckGroupId(Integer id);
}
