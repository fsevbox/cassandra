package org.techfrog.cassandradriver.repository;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.stereotype.Repository;
import org.techfrog.cassandradriver.model.Customer;

import java.util.List;
import java.util.UUID;

import static com.datastax.driver.core.querybuilder.QueryBuilder.select;

@Repository
public class CustomerRepository {

    public static final String TABLE = "customers";
    private Mapper<Customer> mapper;
    private Session session;

    public CustomerRepository(MappingManager mappingManager) {
        this.mapper = mappingManager.mapper(Customer.class);
        this.session = mappingManager.getSession();
    }

    public List<Customer> findAll() {
        final ResultSet result = session.execute(select().all().from(TABLE));
        return mapper.map(result).all();
    }

    public Customer save(Customer customer) {
        mapper.save(customer);
        return customer;
    }

    public void deleteById(UUID id) {
        PreparedStatement statement = session.prepare("DELETE FROM customers WHERE id = ?");
        statement.setConsistencyLevel(ConsistencyLevel.ALL);
        session.execute(statement.bind(id));
    }
}
