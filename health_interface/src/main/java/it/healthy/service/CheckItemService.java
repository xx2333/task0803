package it.healthy.service;

import it.healthy.domain.CheckItem;
import it.healthy.domain.PageResult;
import it.healthy.domain.QueryPageBean;
public interface CheckItemService  {
    void add(CheckItem checkItem);

    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    void delete(Integer id);

    CheckItem findById(Integer id);

    void update(CheckItem checkItem);
}