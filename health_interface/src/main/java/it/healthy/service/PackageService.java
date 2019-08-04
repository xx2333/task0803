package it.healthy.service;

import it.healthy.domain.CheckGroup;
import it.healthy.domain.Package;
import it.healthy.domain.PageResult;
import it.healthy.domain.QueryPageBean;

import java.util.List;

public interface PackageService {
    void add(Package pacKage, Integer[] checkgroupIds);

    List<CheckGroup> findAll();

    PageResult<Package> findPage(QueryPageBean queryPageBean);

    Package getPackageById(Integer id);

    List<Package> getPackage();

}
