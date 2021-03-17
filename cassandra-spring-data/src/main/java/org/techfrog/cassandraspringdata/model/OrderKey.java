package org.techfrog.cassandraspringdata.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
@EqualsAndHashCode
@ToString
@PrimaryKeyClass
public class OrderKey implements Serializable {

    @PrimaryKeyColumn(name = "customer_id", type = PrimaryKeyType.PARTITIONED)
    private final UUID customerId;

    @PrimaryKeyColumn(name = "order_id", type = PrimaryKeyType.CLUSTERED, ordinal = 0)
    private final UUID orderId;

    @PrimaryKeyColumn(name = "order_date", type = PrimaryKeyType.CLUSTERED, ordinal = 1, ordering = Ordering.DESCENDING)
    private final Instant orderDate;
}