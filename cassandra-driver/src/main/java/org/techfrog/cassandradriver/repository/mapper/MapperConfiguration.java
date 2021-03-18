package org.techfrog.cassandradriver.repository.mapper;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.techfrog.cassandradriver.repository.dao.CustomerDao;
import org.techfrog.cassandradriver.repository.dao.OrderDao;

@Configuration
public class MapperConfiguration {

    @Bean
    public CustomerMapper customerMapper(CqlSession session) {
        return new CustomerMapperBuilder(session).build();
    }

    @Bean
    public CustomerDao customerDao(CustomerMapper customerMapper) {
        return customerMapper.customerDao();
    }

    @Bean
    public OrderMapper orderMapper(CqlSession session) {
        return new OrderMapperBuilder(session).build();
    }

    @Bean
    public OrderDao orderDao(OrderMapper orderMapper) {
        return orderMapper.orderDao();
    }

}
