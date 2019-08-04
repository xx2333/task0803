package it.healthy.dao;

import it.healthy.domain.Order;

public interface OrderDao {

    Order findOrder(Order init_order);

    void addOrder(Order order);

    Order findOrderById(Integer id);
}
