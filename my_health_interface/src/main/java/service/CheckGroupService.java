package service;

import com.lwy.entity.PageResult;
import com.lwy.entity.QueryPageBean;
import com.lwy.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    PageResult findPage(QueryPageBean queryPageBean);

    void add(CheckGroup checkGroup, List<Integer> checkitemIds);

    void edit(CheckGroup checkGroup, List<Integer> checkitemIds);

    CheckGroup findById(Integer id);

    List<Integer> findCheckitemIdsById(Integer id);

    List<CheckGroup> findAll();
}
