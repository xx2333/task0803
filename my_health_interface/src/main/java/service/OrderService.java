package service;

import com.lwy.entity.Result;

import java.util.Map;

public interface OrderService {
    Result order(Map orderInfo) throws Exception;

    Map<String, Object> findDetailById(Integer id) throws Exception;
}
