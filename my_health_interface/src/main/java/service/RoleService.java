package service;

import com.lwy.pojo.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
//    List<Role> findByCondition(String queryString);

    void add(Role Role,List<Integer> permissionIds,List<Integer> menuIds);

    void deleteById(Integer id);

    void edit(Role role,List<Integer> permissionIds,List<Integer> menuIds);
}
