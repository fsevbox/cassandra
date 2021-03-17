package org.techfrog.cassandraspringdata.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Getter
@Builder
@ToString
@Table
public class Customer {

    @Id
    private final UUID id;
    private final String firstname, lastname;
}
