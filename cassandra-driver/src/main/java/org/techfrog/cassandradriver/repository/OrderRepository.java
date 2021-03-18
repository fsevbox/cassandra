package org.techfrog.cassandradriver.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.techfrog.cassandradriver.model.Order;
import org.techfrog.cassandradriver.repository.dao.OrderDao;

import java.util.List;
import java.util.UUID;

@Repository
public class OrderRepository {

    private OrderDao orderDao;

    @Autowired
    public OrderRepository(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public List<Order> findByCustomerId(UUID customerId) {
        return orderDao.findByCustomerId(customerId).all();
    }

    public Order findByCustomerIdAndOrderId(UUID customerId, UUID orderId) {
        return orderDao.findByCustomerIdAndOrderId(customerId, orderId);
    }

    public void deleteByCustomerId(UUID customerId) {
        orderDao.deleteByCustomerId(customerId);
    }

    public Order save(Order order) {
        orderDao.save(order);
        return order;
    }
}
