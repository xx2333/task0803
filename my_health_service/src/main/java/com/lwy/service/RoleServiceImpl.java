package com.lwy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lwy.constant.MessageConstant;
import com.lwy.dao.RoleDao;
import com.lwy.exception.HealthException;
import com.lwy.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import service.RoleService;

import java.util.List;

@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

   /* @Override
    public List<Role> findByCondition(String queryString) {
        return null;
    }*/

   @Transactional
    @Override
    public void add(Role role,List<Integer> permissionIds,List<Integer> menuIds) {
            roleDao.add(role);
            setRelation(role.getId(),permissionIds,menuIds);
    }

    /**
     * 删除角色
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(Integer id) {
       long count=roleDao.findCountByRoleId(id);
       if (count>0){
           throw new HealthException(MessageConstant.ROLE_USED);
       }
        clearRelationByRoleId(id);
        roleDao.deleteById(id);
    }

    @Override
    @Transactional
    public void edit(Role role,List<Integer> permissionIds,List<Integer> menuIds) {
        roleDao.edit(role);
        setRelation(role.getId(), permissionIds, menuIds);
    }

    /**
     * 设置角色与菜单、权限的关系
     * @param roleId
     * @param permissionIds
     * @param menuIds
     */
    private void setRelation(Integer roleId,List<Integer> permissionIds,List<Integer> menuIds){
        if (permissionIds!=null&&permissionIds.size()>0){
            permissionIds.forEach(permissionId -> roleDao.setRolePermissionRelation(roleId, permissionId));
        }
        if (menuIds!=null&&menuIds.size()>0){
            menuIds.forEach(menuId->roleDao.setRoleMenuRelation(roleId,menuId));
        }
    }

    /**
     * 清除角色与菜单、权限的关系
     * @param id
     */
    private void clearRelationByRoleId(Integer id){
        roleDao.clearRolePermissionRelationByRoleId(id);
        roleDao.clearRoleMenuRelationByRoleId(id);
    }
}
