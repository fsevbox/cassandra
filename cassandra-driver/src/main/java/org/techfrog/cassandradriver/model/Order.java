package org.techfrog.cassandradriver.model;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "orders")
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
