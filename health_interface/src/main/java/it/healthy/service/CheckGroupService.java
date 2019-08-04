package it.healthy.service;

import it.healthy.domain.CheckGroup;
import it.healthy.domain.CheckItem;
import it.healthy.domain.PageResult;
import it.healthy.domain.QueryPageBean;

import java.util.List;

public interface CheckGroupService {

    List<CheckItem> findAll();

    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult<CheckGroup> findByCondition(QueryPageBean queryPageBean);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    void delete(Integer id);
}
