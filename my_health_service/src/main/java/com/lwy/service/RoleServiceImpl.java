package com.lwy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lwy.constant.MessageConstant;
import com.lwy.dao.RoleDao;
import com.lwy.entity.PageResult;
import com.lwy.entity.QueryPageBean;
import com.lwy.exception.HealthException;
import com.lwy.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import service.RoleService;

import java.util.List;

@SuppressWarnings("ALL")
@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }


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
        clearRelationByRoleId(role.getId());
        setRelation(role.getId(), permissionIds, menuIds);
        roleDao.edit(role);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        String queryString = queryPageBean.getQueryString();
        if (queryString!=null) {
            queryString = "%" + queryString + "%";
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Role> page = roleDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Integer> findPermissionIdsById(Integer id) {
        return roleDao.findPermissionIdsById(id);
    }

    @Override
    public List<Integer> findMenuIdsById(Integer id) {
        return roleDao.findMenuIdsById(id);
    }

    @Override
    public Role findById(Integer id) {
        return roleDao.findById(id);
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
