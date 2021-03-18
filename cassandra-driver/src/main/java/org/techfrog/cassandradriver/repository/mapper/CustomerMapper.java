package org.techfrog.cassandradriver.repository.mapper;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import org.techfrog.cassandradriver.repository.dao.CustomerDao;

@Mapper
public interface CustomerMapper {

    @DaoFactory
    CustomerDao customerDao();
}
