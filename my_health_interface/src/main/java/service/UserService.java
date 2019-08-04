package service;

import com.lwy.entity.PageResult;
import com.lwy.entity.QueryPageBean;
import com.lwy.pojo.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
    List<User> findAll();
//    List<User> findByCondition(String queryString);

    void add(User user, List<Integer> roleIds);

    void deleteById(Integer id);

    void edit(User user,  List<Integer> roleIds);

    PageResult findPage(QueryPageBean queryPageBean);

    User findById(Integer id);

    List<Integer> findRoleIdsById(Integer id);
}
