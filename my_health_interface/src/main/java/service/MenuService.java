package service;

import com.lwy.pojo.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> findByUsername(String username);
    List<Menu> findAll();
//    List<Menu> findByCondition(String queryString);

    void add(Menu menu);

    void deleteById(Integer id);

    void edit(Menu menu);
}
