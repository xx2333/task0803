package it.healthy.dao;

import com.github.pagehelper.Page;
import it.healthy.domain.CheckItem;

import java.util.List;

public interface CheckItemDao {
    void add(CheckItem checkItem);

    Page<CheckItem> findPage(String queryString);

    long findCountByCheckItemId(Integer id);

    void delete(Integer id);

    CheckItem findById(Integer id);

    void update(CheckItem checkItem);

    List<CheckItem> findCheckItemById(int id);
}