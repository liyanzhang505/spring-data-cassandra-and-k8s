package com.lean.cassandra.service;


import com.lean.cassandra.bean.Person;

import java.util.List;
public interface PersonService {
    public Person getPersonById(String id);

    public List<Person> getPersonList();

    public boolean delete(String id);
}