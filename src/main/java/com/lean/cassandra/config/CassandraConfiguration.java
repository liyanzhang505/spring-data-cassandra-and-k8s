package com.lean.cassandra.config;

import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.*;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.core.cql.session.init.KeyspacePopulator;
import org.springframework.data.cassandra.core.cql.session.init.ResourceKeyspacePopulator;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;


@Configuration
public class CassandraConfiguration extends AbstractCassandraConfiguration implements BeanClassLoaderAware {

    @Value("${spring.data.cassandra.contact-points}")
    private String contactPoints;

    @Value("${spring.data.cassandra.key-space}")
    private String keySpace;

    @Value("${basic.load-balancing-policy.local-datacenter}")
    private String datacenter;

    /*
     * Provide a contact point to the configuration.
     */
    @Override
    public String getContactPoints() {
        return contactPoints;
    }

    /*
     * Provide a keyspace name to the configuration.
     */
    @Override
    public String getKeyspaceName() {
        return keySpace;
    }


    /*
     * Provide a datacenter to the configuration.
     */
    @Nullable
    @Override
    protected String getLocalDataCenter() {
        return datacenter;
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification.createKeyspace(keySpace)
                .with(KeyspaceOption.DURABLE_WRITES, true)
                .ifNotExists()
                .withSimpleReplication(1);
//                .withNetworkReplication(DataCenterReplication.of(datacenter, 3));
        return Arrays.asList(specification);
    }

    @Override
    protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
        return Arrays.asList(DropKeyspaceSpecification.dropKeyspace(keySpace));
    }

    @Nullable
    @Override
    protected KeyspacePopulator keyspacePopulator() {
        return new ResourceKeyspacePopulator(
                scriptOf("create table if not exists person (id text primary key, name text,age int);"),
                scriptOf("insert into person (id,name,age) values ('1', 'lyz',20);"));
    }

    @Nullable
    @Override
    protected KeyspacePopulator keyspaceCleaner() {
        return new ResourceKeyspacePopulator(scriptOf("DROP TABLE  if exists person;"));
    }

    /* *
    * Query timeout maybe happened when using local driver to connect remote cassandra service,
    * so we should configure timeout setting as blow.
    */
    @Override
    protected SessionBuilderConfigurer getSessionBuilderConfigurer() {
        return new SessionBuilderConfigurer() {
            @Override
            public CqlSessionBuilder configure(CqlSessionBuilder cqlSessionBuilder) {
                return cqlSessionBuilder
                        .withConfigLoader(DriverConfigLoader.programmaticBuilder().withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofMillis(15000)).build());
            }
        };
    }
}