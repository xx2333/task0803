package service;

import com.lwy.entity.PageResult;
import com.lwy.entity.QueryPageBean;
import com.lwy.pojo.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionService {
    Set<Permission> findByRoleId(Integer id);
    List<Permission> findAll();
    PageResult findPage(QueryPageBean queryPageBean);

    void add(Permission permission);

    void deleteById(Integer id);

    void edit(Permission permission);
}
