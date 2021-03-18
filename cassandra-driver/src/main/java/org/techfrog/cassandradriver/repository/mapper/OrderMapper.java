package org.techfrog.cassandradriver.repository.mapper;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import org.techfrog.cassandradriver.repository.dao.OrderDao;

@Mapper
public interface OrderMapper {

    @DaoFactory
    OrderDao orderDao();
}
