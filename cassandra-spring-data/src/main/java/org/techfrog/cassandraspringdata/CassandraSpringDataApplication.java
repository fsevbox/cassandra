package org.techfrog.cassandraspringdata;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.techfrog.cassandraspringdata.model.Customer;
import org.techfrog.cassandraspringdata.model.Order;
import org.techfrog.cassandraspringdata.model.OrderKey;
import org.techfrog.cassandraspringdata.repository.CustomerRepository;
import org.techfrog.cassandraspringdata.repository.OrderRepository;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@SpringBootApplication
public class CassandraSpringDataApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CassandraSpringDataApplication.class, args);
    }

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void run(String... args) {
        generateData();

        customerRepository.findAll()
                .forEach(customer -> {
                            List<Order> ordersForCustomer = orderRepository.findByKeyCustomerId(customer.getId());
                            System.out.println("Orders by customer id: " + ordersForCustomer + " | " + customer.getId());

                            ordersForCustomer.stream()
                                    .forEach(o -> {
                                        Order order = orderRepository.findByKeyCustomerIdAndKeyOrderId(customer.getId(), o.getKey().getOrderId());
                                        System.out.println("Order: " + order);
                                    });

                            System.out.println("Deleting orders for customer: " + customer.getId());
                            orderRepository.deleteAllByKeyCustomerId(customer.getId());
                        }
                );

        System.out.println("Deleting customers ");
        customerRepository.deleteAll();

        System.exit(0);
    }

    private void generateData() {
        Customer tom = Customer.builder()
                .id(Uuids.timeBased())
                .firstname("tom")
                .lastname("jones")
                .build();
        customerRepository.save(tom);

        Order firstOrder = Order.builder()
                .key(
                        OrderKey.builder()
                                .customerId(tom.getId())
                                .orderId(Uuids.timeBased())
                                .orderDate(Instant.now())
                                .build()
                )
                .product("cat food")
                .quantity(2)
                .build();
        orderRepository.save(firstOrder);

        Order secondOrder = Order.builder()
                .key(
                        OrderKey.builder()
                                .customerId(tom.getId())
                                .orderId(Uuids.timeBased())
                                .orderDate(ZonedDateTime.now()
                                        .with(TemporalAdjusters.lastDayOfYear())
                                        .toInstant())
                                .build()
                )
                .product("cat mansion")
                .quantity(1)
                .build();
        orderRepository.save(secondOrder);
    }
}
