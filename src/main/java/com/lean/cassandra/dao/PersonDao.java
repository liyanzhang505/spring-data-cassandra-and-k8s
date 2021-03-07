package com.lean.cassandra.dao;

import com.lean.cassandra.bean.Person;
import java.util.List;

public interface PersonDao {
    public Person getPersonById(String id);

    public List<Person> getPersonList();

    public boolean delete(String id);
}