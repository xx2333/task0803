package com.lwy.dao;

import com.github.pagehelper.Page;
import com.lwy.pojo.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionDao {
    Set<Permission> findByRoleId(Integer id);
    List<Permission> findAll();
    Page<Permission> findByCondition(String queryString);

    void add(Permission permission);

    void deleteById(Integer id);

    void edit(Permission permission);

    long findCountById(Integer id);
}
