package com.lean.cassandra.web;

import com.lean.cassandra.bean.JsonResult;
import com.lean.cassandra.bean.Person;
import com.lean.cassandra.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class WebController {
    @Autowired
    private PersonService personService;

    @RequestMapping(value = "person/{id}", method = RequestMethod.GET)
    public JsonResult getPersonById (@PathVariable(value = "id") String id){
        JsonResult r = new JsonResult();
        try {
            Person person = personService.getPersonById(id);
            r.setResult(person);
            r.setStatus("ok");
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus("error");
            e.printStackTrace();
        }
        return r;
    }

    @RequestMapping(value = "persons", method = RequestMethod.GET)
    public JsonResult getPersonList (){
        JsonResult r = new JsonResult();
        try {
            List<Person> persons = personService.getPersonList();
            r.setResult(persons);
            r.setStatus("ok");
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus("error");
            e.printStackTrace();
        }
        return r;
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public JsonResult delete (@PathVariable(value = "id") String id){
        JsonResult r = new JsonResult();
        try {
            boolean orderId = personService.delete(id);
            if (!orderId) {
                r.setResult(orderId);
                r.setStatus("fail");
            } else {
                r.setResult(orderId);
                r.setStatus("ok");
            }
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus("error");

            e.printStackTrace();
        }
        return r;
    }
}