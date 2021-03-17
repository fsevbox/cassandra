package org.techfrog.cassandraspringdata.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Builder
@ToString
@Table("order")
public class Order {

    @PrimaryKey
    private final OrderKey key;

    private final String product;

    private final int quantity;
}
