package it.healthy.dao;

import com.github.pagehelper.Page;
import it.healthy.domain.CheckGroup;
import it.healthy.domain.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupDao {
    List<CheckItem> findAll();

    void addGroup(CheckGroup checkGroup);

    void addGroupAndItem(@Param("checkgroup_id") Integer id, @Param("checkitem_id") Integer checkitem_id);

    Page<CheckGroup> findPage(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void updateByid(CheckGroup checkGroup);

    void deleteCheckItemByCheckGroupId(int checkGroupId);

    void deleteCheckGroup(Integer id);

    List<CheckGroup> findCheckGroupById(Integer id);
}
