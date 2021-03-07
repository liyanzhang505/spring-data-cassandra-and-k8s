package com.lean.cassandra.service.impl;

import com.lean.cassandra.bean.Person;
import com.lean.cassandra.dao.PersonDao;
import com.lean.cassandra.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonDao personDao;

    @Override
    public Person getPersonById(String id){
        return personDao.getPersonById(id);
    }

    @Override
    public List<Person> getPersonList(){
        return personDao.getPersonList();
    }

    @Override
    public boolean delete(String id){
        return personDao.delete(id);
    }
}