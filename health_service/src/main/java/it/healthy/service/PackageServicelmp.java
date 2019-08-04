package it.healthy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import it.healthy.Utils.StringUtils;
import it.healthy.dao.PackageDao;
import it.healthy.domain.CheckGroup;
import it.healthy.domain.Package;
import it.healthy.domain.PageResult;
import it.healthy.domain.QueryPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = PackageService.class)
public class PackageServicelmp implements PackageService {
    @Autowired
    private PackageDao packageDao;
    @Transactional
    @Override
    public void add(Package pacKage, Integer[] checkgroupIds) {
        packageDao.addPackage(pacKage);
        if(checkgroupIds==null) return;
        for (Integer checkgroupId : checkgroupIds) {
            packageDao.addCheckGroupByPackage(pacKage.getId(),checkgroupId);
        }
    }

    @Override
    public List<CheckGroup> findAll() {
        return packageDao.findAll();
    }

    @Override
    public PageResult<Package> findPage(QueryPageBean queryPageBean) {
        if(!StringUtils.IsEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Package> page=packageDao.findPage(queryPageBean.getQueryString());
        return new PageResult<Package>(page.getTotal(), page.getResult());
    }

    @Override
    public Package getPackageById(Integer id) {
        return packageDao.getPackageById(id);
    }

    @Override
    public List<Package> getPackage() {
        return packageDao.getPackage();
    }
}
