package org.techfrog.cassadracql.model;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "customers")
public class Customer {

    @PartitionKey
    private UUID id;
    private String firstname, lastname;
}
