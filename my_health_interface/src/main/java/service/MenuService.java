package service;

import com.lwy.entity.PageResult;
import com.lwy.entity.QueryPageBean;
import com.lwy.exception.HealthException;
import com.lwy.pojo.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> findByUsername(String username);
    List<Menu> findAll();
    void add(Menu menu) throws HealthException;

    void deleteById(Integer id);

    void edit(Menu menu)throws HealthException;

    PageResult findPage(QueryPageBean queryPageBean);

    Menu findById(Integer id);
}
