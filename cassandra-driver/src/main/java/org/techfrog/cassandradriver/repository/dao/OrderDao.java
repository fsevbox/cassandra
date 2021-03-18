package org.techfrog.cassandradriver.repository.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import org.techfrog.cassandradriver.model.Order;

import java.util.UUID;

@Dao
public interface OrderDao {

    @Select
    PagingIterable<Order> findById(UUID customerId);

    @Insert
    boolean save(Order order);

    @Select
    PagingIterable<Order> findByCustomerId(UUID customerId);

    @Select
    Order findByCustomerIdAndOrderId(UUID customerId, UUID orderId);

    @Delete(entityClass = Order.class)
    void deleteByCustomerId(UUID customerId);
}
