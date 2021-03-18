package org.techfrog.cassandradriver.repository.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import org.techfrog.cassandradriver.model.Customer;

import java.util.Optional;
import java.util.UUID;

@Dao
public interface CustomerDao {

    @Select
    PagingIterable<Customer> findAll();

    @Select
    Optional<Customer> findById(UUID id);

    @Insert
    boolean save(Customer customer);

    @Delete(entityClass = Customer.class)
    boolean delete(UUID id);
}
