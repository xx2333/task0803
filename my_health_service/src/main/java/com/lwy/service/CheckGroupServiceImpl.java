package com.lwy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.validation.MethodValidated;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lwy.dao.CheckGroupDao;
import com.lwy.entity.PageResult;
import com.lwy.entity.QueryPageBean;
import com.lwy.pojo.CheckGroup;
import org.springframework.beans.factory.annotation.Autowired;
import service.CheckGroupService;

import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckGroup> page =checkGroupDao.selectByCondition(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(),page.getResult());
    }


    @Override
    public void add(CheckGroup checkGroup, List<Integer> checkitemIds) {
        checkGroupDao.add(checkGroup);
        if (checkitemIds!=null&&checkitemIds.size()>0){
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.setCheckGroupAndCheckItemRelation(checkGroup.getId(),checkitemId);
            }
        }
    }

    @Override
    public void edit(CheckGroup checkGroup, List<Integer> checkitemIds) {
        checkGroupDao.edit(checkGroup);
        Integer id = checkGroup.getId();
        checkGroupDao.clearCheckGroupAndCheckItemRelation(id);
        if (checkitemIds!=null&&checkitemIds.size()>0){
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.setCheckGroupAndCheckItemRelation(id,checkitemId);
            }
        }
    }


    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckitemIdsById(Integer id) {
        return checkGroupDao.findCheckitemIdsById(id);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
