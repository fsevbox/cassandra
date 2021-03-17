package org.techfrog.cassandraspringdata.repository;

import org.springframework.data.repository.Repository;
import org.techfrog.cassandraspringdata.model.Customer;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface CustomerRepository extends Repository<Customer, String> {

    Optional<Customer> findById(UUID id);

    Stream<Customer> findAll();

    void deleteAll();

    Customer save(Customer customer);
}
