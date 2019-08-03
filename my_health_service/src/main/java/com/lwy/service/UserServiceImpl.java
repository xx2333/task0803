package com.lwy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lwy.dao.PermissionDao;
import com.lwy.dao.RoleDao;
import com.lwy.dao.UserDao;
import com.lwy.pojo.Permission;
import com.lwy.pojo.Role;
import com.lwy.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import service.UserService;

import java.util.List;
import java.util.Set;

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
    public void add(User user) {
        userDao.add(user);
    }



    @Override
    @Transactional
    public void deleteById(Integer id) {
        userDao.cleaRelationByUserId(id);
        userDao.deleteById(id);
    }

    @Override
    @Transactional
    public void edit(User user) {

    }

}
