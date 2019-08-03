package com.lwy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lwy.dao.MenuDao;
import com.lwy.exception.HealthException;
import com.lwy.pojo.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import service.MenuService;

import java.util.List;

@Service(interfaceClass = MenuService.class)
public class MenuServiceImpl  implements MenuService{
    @Autowired
    private MenuDao menuDao;

    @Override
    public List<Menu> findByUsername(String username) {
        return menuDao.findByUsername(username);
    }

    @Override
    public List<Menu> findAll() {
        return menuDao.findAll();
    }

    @Override
    public void add(Menu menu) {
        Integer parentMenuId = menu.getParentMenuId();
        if (parentMenuId!=null){
            Menu parentMenu=menuDao.findById(parentMenuId);
            if (parentMenu==null){
                throw new HealthException("您输入的父级菜单不存在");
            }
        }
        menuDao.add(menu);
    }

    @Override
    public void deleteById(Integer id) {
        menuDao.deleteById(id);
    }

    @Override
    public void edit(Menu menu) {
        Integer parentMenuId = menu.getParentMenuId();
        if (parentMenuId!=null){
            Menu parentMenu=menuDao.findById(parentMenuId);
            if (parentMenu==null){
                throw new HealthException("您输入的父级菜单不存在");
            }
        }
        menuDao.edit(menu);
    }

}
