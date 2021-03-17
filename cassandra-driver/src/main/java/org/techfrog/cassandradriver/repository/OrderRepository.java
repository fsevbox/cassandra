package org.techfrog.cassandradriver.repository;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.stereotype.Repository;
import org.techfrog.cassandradriver.model.Order;

import java.util.List;
import java.util.UUID;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;

@Repository
public class OrderRepository {

    public static final String TABLE = "orders";
    private Mapper<Order> mapper;
    private Session session;

    public OrderRepository(MappingManager mappingManager) {
        this.mapper = mappingManager.mapper(Order.class);
        this.session = mappingManager.getSession();
    }

    public List<Order> findByKeyCustomerId(UUID customerId) {
        final ResultSet result = session.execute(
                select()
                        .all()
                        .from(TABLE)
                        .where(eq("customer_id", customerId))
                        .setConsistencyLevel(ConsistencyLevel.LOCAL_QUORUM));
        return mapper.map(result).all();
    }

    public Order findByKeyCustomerIdAndKeyOrderId(UUID customerId, UUID orderId) {
        final ResultSet result = session.execute(
                select()
                        .all()
                        .from(TABLE)
                        .where(eq("customer_id", customerId))
                        .and(eq("order_id", orderId))
                        .setConsistencyLevel(ConsistencyLevel.ONE));
        return mapper.map(result).one();
    }

    public void deleteAllByKeyCustomerId(UUID customerId) {
        SimpleStatement statement = new SimpleStatement(
                "DELETE FROM orders WHERE customer_id = ?", customerId);
        statement.setConsistencyLevel(ConsistencyLevel.ALL);
        session.execute(statement);
    }

    public Order save(Order order) {
        mapper.save(order);
        return order;
    }
}
