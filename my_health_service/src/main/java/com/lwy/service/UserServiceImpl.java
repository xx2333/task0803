package com.lwy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lwy.dao.PermissionDao;
import com.lwy.dao.RoleDao;
import com.lwy.dao.UserDao;
import com.lwy.entity.PageResult;
import com.lwy.entity.QueryPageBean;
import com.lwy.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import service.UserService;

import java.util.List;

@SuppressWarnings("all")
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;
    @Override
    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);
        System.out.println(user);
      /*  if (user==null) {
            return null;
        }
        Set<Role> roles=roleDao.findByUserId(user.getId());
        if (roles!=null) {
            for (Role role : roles) {
                Set<Permission> permissions=permissionDao.findByRoleId(role.getId());
                role.setPermissions(permissions);
            }
            user.setRoles(roles);
        }*/
        return user;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public void add(User user,  List<Integer> roleIds) {
        userDao.add(user);
        setUserRoleRelation(user.getId(),roleIds);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        userDao.cleaRelationByUserId(id);
        userDao.deleteById(id);
    }

    @Override
    @Transactional
    public void edit(User user,  List<Integer> roleIds) {
        userDao.cleaRelationByUserId(user.getId());
        setUserRoleRelation(user.getId(),roleIds);
        userDao.edit(user);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        String queryString = queryPageBean.getQueryString();
        if (queryString!=null) {
            queryString = "%" + queryString + "%";
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<User> page = userDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public List<Integer> findRoleIdsById(Integer id) {
        return userDao.findRoleIdsById(id);
    }

    public void setUserRoleRelation(Integer uid,List<Integer> roleIds){
        if (roleIds!=null&&roleIds.size()>0){
            roleIds.forEach(roleId->userDao.serUserRoleRelation(uid,roleId));
        }
    }
}
