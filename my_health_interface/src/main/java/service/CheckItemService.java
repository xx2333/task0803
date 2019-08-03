package service;

import com.lwy.entity.PageResult;
import com.lwy.entity.QueryPageBean;
import com.lwy.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    List<CheckItem> findAll();

    void add(CheckItem checkItem);

    PageResult findPage(QueryPageBean queryPageBean);

    void edit(CheckItem checkItem);

    void deleteById(Integer id);
}
