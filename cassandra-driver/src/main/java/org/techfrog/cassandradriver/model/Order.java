package org.techfrog.cassandradriver.model;

import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@CqlName("orders")
public class Order {

    @PartitionKey
    private UUID customerId;

    @ClusteringColumn
    private UUID orderId;

    @ClusteringColumn(1)
    private Instant orderDate;

    private String product;

    private int quantity;

}
