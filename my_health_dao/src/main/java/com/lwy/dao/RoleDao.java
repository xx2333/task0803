package com.lwy.dao;

import com.lwy.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface RoleDao {
    Set<Role> findByUserId(Integer id);
    List<Role> findAll();
    List<Role> findByCondition(String queryString);

    void add(Role Role);

    void deleteById(Integer id);

    void edit(Role Role);



    void setRolePermissionRelation(@Param("roleId") Integer roleId,@Param("permissionId") Integer permissionId);

    void setRoleMenuRelation(@Param("roleId") Integer roleId,@Param("menuId") Integer menuId);

    void clearRolePermissionRelationByRoleId(Integer id);

    void clearRoleMenuRelationByRoleId(Integer id);

    long findCountByRoleId(Integer id);
}
