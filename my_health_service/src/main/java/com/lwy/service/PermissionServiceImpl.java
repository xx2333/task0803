package com.lwy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lwy.dao.PermissionDao;
import com.lwy.entity.PageResult;
import com.lwy.entity.QueryPageBean;
import com.lwy.exception.HealthException;
import com.lwy.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import service.PermissionService;

import java.util.List;
import java.util.Set;

@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;


    @Override
    public Set<Permission> findByRoleId(Integer id) {
        return permissionDao.findByRoleId(id);
    }

    @Override
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        String queryString = queryPageBean.getQueryString();
        if (queryString !=null){
            queryString="%"+queryString+"%";
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        Page<Permission> page = permissionDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void add(Permission permission) {
        permissionDao.add(permission);
    }

    @Override
    public void deleteById(Integer id) throws HealthException{
        long count = permissionDao.findCountById(id);
        if (count>0){
            throw new HealthException("权限已被引用");
        }
        permissionDao.deleteById(id);
    }

    @Override
    public void edit(Permission permission) {
        permissionDao.edit(permission);
    }
}
