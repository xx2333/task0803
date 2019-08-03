package com.lwy.service;

import com.alibaba.dubbo.config.annotation.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lwy.constant.RedisConstant;
import com.lwy.dao.SetmealDao;
import com.lwy.entity.PageResult;
import com.lwy.entity.QueryPageBean;
import com.lwy.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;
import service.SetmealService;

import java.util.List;

@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;

    @Transactional
    @Override
    public void add(Setmeal setmeal, List<Integer> checkgroupIds) {
        setmealDao.add(setmeal);
        Integer id = setmeal.getId();
        if (checkgroupIds != null && checkgroupIds.size() > 0) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.setSetmealCheckGroupRelation(id, checkgroupId);
            }
        }

        if (setmeal.getImg() != null) {
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
        }

    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Setmeal> page = setmealDao.selectByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }
}
