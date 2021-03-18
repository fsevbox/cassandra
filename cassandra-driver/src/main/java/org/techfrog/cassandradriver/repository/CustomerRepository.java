package org.techfrog.cassandradriver.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.techfrog.cassandradriver.model.Customer;
import org.techfrog.cassandradriver.repository.dao.CustomerDao;

import java.util.List;
import java.util.UUID;

@Repository
public class CustomerRepository {

    private CustomerDao customerDao;

    @Autowired
    public CustomerRepository(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> findAll() {
        return customerDao.findAll().all();
    }

    public Customer save(Customer customer) {
        customerDao.save(customer);
        return customer;
    }

    public void deleteById(UUID id) {
        customerDao.delete(id);
    }
}
