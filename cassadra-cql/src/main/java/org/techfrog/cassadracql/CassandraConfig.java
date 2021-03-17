package org.techfrog.cassadracql;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.extras.codecs.jdk8.InstantCodec;
import com.datastax.driver.mapping.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.datastax.driver.core.DataType.*;
import static com.datastax.driver.core.schemabuilder.SchemaBuilder.createKeyspace;
import static com.datastax.driver.core.schemabuilder.SchemaBuilder.createTable;
import static com.datastax.driver.mapping.NamingConventions.LOWER_CAMEL_CASE;
import static com.datastax.driver.mapping.NamingConventions.LOWER_SNAKE_CASE;

@Configuration
public class CassandraConfig {

    @Bean
    public Cluster cluster(
            @Value("${cassandra.host:127.0.0.1}") String host,
            @Value("${cassandra.cluster.name:cluster}") String clusterName,
            @Value("${cassandra.port:9042}") int port) {

        Cluster cluster = Cluster.builder()
                .addContactPoint(host)
                .withPort(port)
                .withClusterName(clusterName)
                .withoutMetrics()
                .build();

        cluster.getConfiguration().getCodecRegistry()
                .register(InstantCodec.instance);

        return cluster;
    }

    @Bean
    public Session session(Cluster cluster, @Value("${cassandra.keyspace}") String keyspace)
            throws IOException {
        final Session session = cluster.connect();
        setupKeyspace(session, keyspace);
        createTables(session);
        return session;
    }

    private void setupKeyspace(Session session, String keyspace) throws IOException {
        final Map<String, Object> replication = new HashMap<>();
        replication.put("class", "SimpleStrategy");
        replication.put("replication_factor", 1);
        session.execute(createKeyspace(keyspace)
                .ifNotExists()
                .with()
                .replication(replication));
        session.execute("USE " + keyspace);
    }

    private void createTables(Session session) {
        session.execute(createTable("customers")
                .ifNotExists()
                .addPartitionKey("id", uuid())
                .addColumn("firstname", text())
                .addColumn("lastname", text()));

        session.execute(createTable("orders")
                .ifNotExists()
                .addPartitionKey("customer_id", uuid())
                .addClusteringColumn("order_id", uuid())
                .addClusteringColumn("order_date", timestamp())
                .addColumn("product", text())
                .addColumn("quantity", cint()));
    }

    @Bean
    public MappingManager mappingManager(Session session) {
        final PropertyMapper propertyMapper =
                new DefaultPropertyMapper()
                        .setNamingStrategy(new DefaultNamingStrategy(LOWER_CAMEL_CASE, LOWER_SNAKE_CASE));
        final MappingConfiguration configuration =
                MappingConfiguration.builder().withPropertyMapper(propertyMapper).build();
        return new MappingManager(session, configuration);
    }

}
