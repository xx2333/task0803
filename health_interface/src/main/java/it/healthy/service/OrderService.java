package it.healthy.service;

import it.healthy.domain.Order;

import java.util.Map;

public interface OrderService {
    Integer orderService(Map map) throws Exception;

    Order findOrderById(Integer id);
}
