package org.techfrog.cassandradriver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.techfrog.cassandradriver.model.Customer;
import org.techfrog.cassandradriver.model.Order;
import org.techfrog.cassandradriver.repository.CustomerRepository;
import org.techfrog.cassandradriver.repository.OrderRepository;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class CassandraDriverApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CassandraDriverApplication.class, args);
    }

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {
        generateData();

        List<Customer> customers = customerRepository.findAll();
        customers.stream()
                .forEach(customer -> {
                            List<Order> ordersForCustomer = orderRepository.findByKeyCustomerId(customer.getId());
                            System.out.println("Orders by customer id: " + ordersForCustomer + " | " + customer.getId());

                            ordersForCustomer.stream()
                                    .forEach(o -> {
                                        Order order = orderRepository.findByKeyCustomerIdAndKeyOrderId(customer.getId(), o.getOrderId());
                                        System.out.println("Order: " + order);
                                    });

                            System.out.println("Deleting orders for customer: " + customer.getId());
                            orderRepository.deleteAllByKeyCustomerId(customer.getId());

                            System.out.println("Deleting customer: " + customer.getId());
                            customerRepository.deleteById(customer.getId());
                        }
                );

        System.exit(0);
    }

    private void generateData() {
        Customer tom = Customer.builder()
                .id(UUID.randomUUID())
                .firstname("tom")
                .lastname("jones")
                .build();
        customerRepository.save(tom);

        Order firstOrder = Order.builder()
                .customerId(tom.getId())
                .orderId(UUID.randomUUID())
                .orderDate(Instant.now())
                .product("cat food")
                .quantity(2)
                .build();
        orderRepository.save(firstOrder);

        Order secondOrder = Order.builder()
                .customerId(tom.getId())
                .orderId(UUID.randomUUID())
                .orderDate(ZonedDateTime.now()
                        .with(TemporalAdjusters.lastDayOfYear())
                        .toInstant())
                .product("cat mansion")
                .quantity(1)
                .build();
        orderRepository.save(secondOrder);
    }
}
