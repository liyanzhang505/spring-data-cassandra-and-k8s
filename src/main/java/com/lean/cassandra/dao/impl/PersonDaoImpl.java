package com.lean.cassandra.dao.impl;

import com.lean.cassandra.bean.Person;
import com.lean.cassandra.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class PersonDaoImpl implements PersonDao {

    @Autowired
    private CassandraOperations cassandraOperations;

    @Override
    public Person getPersonById(String id){
        Person person = cassandraOperations.selectOne(Query.query(Criteria.where("id").is(id)), Person.class);
        return person;
    }

    @Override
    public List<Person> getPersonList(){
        List<Person> list = cassandraOperations.select("select * from person", Person.class);
        if(list!=null && list.size()>0){
            return list;
        }else{
            return null;
        }
    }

    @Override
    public boolean delete(String id){
        // Method 1
//       return cassandraTemplate.delete(Query.query(Criteria.where("id").is(id)), Person.class);
        // Method 2
        return cassandraOperations.deleteById(id, Person.class);
    }
}