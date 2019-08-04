package it.healthy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import it.healthy.Utils.StringUtils;
import it.healthy.dao.CheckGroupDao;
import it.healthy.domain.CheckGroup;
import it.healthy.domain.CheckItem;
import it.healthy.domain.PageResult;
import it.healthy.domain.QueryPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServicelmp implements CheckGroupService, Serializable {
    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public List<CheckItem> findAll() {
        return checkGroupDao.findAll();
    }

    @Transactional
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.addGroup(checkGroup);
        for (Integer checkitemId : checkitemIds) {
            checkGroupDao.addGroupAndItem(checkGroup.getId(),checkitemId);
        }
    }

    @Override
    public PageResult<CheckGroup> findByCondition(@RequestBody QueryPageBean queryPageBean) {
        if(!StringUtils.IsEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString( "%" + queryPageBean.getQueryString()+ "%");
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckGroup> page=checkGroupDao.findPage(queryPageBean.getQueryString());
        return new PageResult<CheckGroup>(page.getTotal(),page.getResult());
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    @Override
    @Transactional
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.updateByid(checkGroup);
        checkGroupDao.deleteCheckItemByCheckGroupId(checkGroup.getId());
        for (Integer checkitemId : checkitemIds) {
            checkGroupDao.addGroupAndItem(checkGroup.getId(),checkitemId);
        }
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        checkGroupDao.deleteCheckItemByCheckGroupId(id);
        checkGroupDao.deleteCheckGroup(id);
    }
}
