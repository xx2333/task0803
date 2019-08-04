package it.healthy.dao;

import com.github.pagehelper.Page;
import it.healthy.domain.CheckGroup;
import it.healthy.domain.Package;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PackageDao {
    void addPackage(Package pacKage);

    void addCheckGroupByPackage(@Param("packageId") Integer packageId, @Param("checkgroupId") Integer checkgroupId);

    List<CheckGroup> findAll();

    Page<Package> findPage(String queryPageBean);

    Package getPackageById(Integer id);

    List<Package> getPackage();
}
