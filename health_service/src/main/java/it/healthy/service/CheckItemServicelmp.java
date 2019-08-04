package it.healthy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import it.healthy.Utils.StringUtils;
import it.healthy.dao.CheckItemDao;
import it.healthy.domain.CheckItem;
import it.healthy.domain.PageResult;
import it.healthy.domain.QueryPageBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

@Service(interfaceClass = CheckItemService.class)
public class CheckItemServicelmp implements CheckItemService, Serializable {
    @Autowired
    private CheckItemDao checkItemDao;
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        if(!StringUtils.IsEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckItem> page=checkItemDao.findPage(queryPageBean.getQueryString());
        return new PageResult<CheckItem>(page.getTotal(),page.getResult());
    }

    @Override
    public void delete(Integer id) {
        long count = checkItemDao.findCountByCheckItemId(id);
        if(count>0){
            throw new RuntimeException("当前检查项被引用，不能删除");
        }
        try {
            checkItemDao.delete(id);
        } catch(RuntimeException e) {
            throw new RuntimeException("系统异常,请稍后再试");
        }

    }

    @Override
    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }

    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

}
