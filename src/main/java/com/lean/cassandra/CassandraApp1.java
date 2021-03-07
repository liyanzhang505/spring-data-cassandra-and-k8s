package com.lean.cassandra;

import java.util.UUID;
import com.lean.cassandra.bean.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;

import com.datastax.oss.driver.api.core.CqlSession;

/**
 * You should create keyspace and table before this demo.
 * create keysapces my_test2 WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'};
 * create table if not exists person (id text primary key, name text,age int);
 */
public class CassandraApp1 {

    private static final Logger LOGGER = LoggerFactory.getLogger(CassandraApp1.class);

    private static Person newPerson(String name, int age) {
        return new Person(UUID.randomUUID().toString(), name, age);
    }

    public static void main(String[] args) {

        CqlSession cqlSession = CqlSession.builder().withKeyspace("my_test1").build();

        CassandraOperations template = new CassandraTemplate(cqlSession);

        Person jonDoe = template.insert(newPerson("Jon Doe", 40));
        Person ret = template.selectOne(Query.query(Criteria.where("id").is(jonDoe.getId())), Person.class);
        System.out.println("====result " + ret.toString());

        // Delete person table
//        template.truncate(Person.class);
        cqlSession.close();
    }

}