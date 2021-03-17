package org.techfrog.cassandraspringdata.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import org.techfrog.cassandraspringdata.model.Order;
import org.techfrog.cassandraspringdata.model.OrderKey;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends CassandraRepository<Order, OrderKey> {

    List<Order> findByKeyCustomerId(UUID customerId);

    Order findByKeyCustomerIdAndKeyOrderId(UUID customerId, UUID orderId);

    void deleteAllByKeyCustomerId(UUID customerId);

    void deleteAll();

    Order save(Order order);
}
