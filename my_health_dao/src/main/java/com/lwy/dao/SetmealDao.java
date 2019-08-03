package com.lwy.dao;

import com.github.pagehelper.Page;
import com.lwy.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    void setSetmealCheckGroupRelation(@Param("setmealId") Integer setmealId,@Param("checkgroupId") Integer checkgroupId);

    void add(Setmeal setmeal);

    Page<Setmeal> selectByCondition(String queryString);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);

    List<Map<String, Object>> getSetmealReport();

    List<Map<String, Object>> getHotSetmeal();
}
