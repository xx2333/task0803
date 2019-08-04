package com.lwy.dao;

import com.github.pagehelper.Page;
import com.lwy.pojo.Menu;

import java.util.List;

public interface MenuDao {
    List<Menu> findByUsername(String username);
    List<Menu> findAll();
    Page<Menu> findByCondition(String queryString);

    void add(Menu menu);

    void deleteById(Integer id);

    void edit(Menu menu);

    Menu findById(Integer id);

    long findCountByMenuId(Integer id);
}
