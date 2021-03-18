package org.techfrog.cassandradriver;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.metadata.schema.ClusteringOrder;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createTable;

@Slf4j
@Configuration
public class CassandraConfig {

    @Bean
    public CqlSession session(@Value("${cassandra.host:127.0.0.1}") String host,
                              @Value("${cassandra.port:9042}") int port,
                              @Value("${cassandra.datacenter:cluster}") String datacenter,
                              @Value("${cassandra.keyspace}") String keyspace) {
        CqlSession cqlSession = CqlSession.builder()
                .addContactPoint(new InetSocketAddress(host, port))
                .withKeyspace(keyspace)
                .withLocalDatacenter(datacenter)
                .build();
        log.info("[OK] Connected to Keyspace {} on node {}", keyspace, host);

        setupKeyspace(cqlSession, keyspace);
        createTables(cqlSession);
        return cqlSession;
    }


    private void setupKeyspace(CqlSession cqlSession, String keyspace) {
        cqlSession.execute(createKeyspaceSimpleStrategy(keyspace, 1));
        log.info("Keyspace '{}' created (if needed).", keyspace);
    }

    private SimpleStatement createKeyspaceSimpleStrategy(String keyspaceName, int replicationFactor) {
        return SchemaBuilder.createKeyspace(keyspaceName)
                .ifNotExists()
                .withSimpleStrategy(replicationFactor)
                .withDurableWrites(true)
                .build();
    }

    private void createTables(CqlSession cqlSession) {
        cqlSession.execute(createTable("customers")
                .ifNotExists()
                .withPartitionKey("id", DataTypes.UUID)
                .withColumn("firstname", DataTypes.TEXT)
                .withColumn("lastname", DataTypes.TEXT)
                .build());

        cqlSession.execute(createTable("orders")
                .ifNotExists()
                .withPartitionKey("customer_id", DataTypes.UUID)
                .withClusteringColumn("order_id", DataTypes.UUID)
                .withClusteringColumn("order_date", DataTypes.TIMESTAMP)
                .withColumn("product", DataTypes.TEXT)
                .withColumn("quantity", DataTypes.INT)
                .withClusteringOrder("order_id", ClusteringOrder.ASC)
                .withClusteringOrder("order_date", ClusteringOrder.DESC)
                .build());
    }

}
