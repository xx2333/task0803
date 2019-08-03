package service;

import com.lwy.pojo.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
    List<User> findAll();
//    List<User> findByCondition(String queryString);

    void add(User user);

    void deleteById(Integer id);

    void edit(User user);

}
