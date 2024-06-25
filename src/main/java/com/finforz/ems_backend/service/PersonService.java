package com.finforz.ems_backend.service;

import com.finforz.ems_backend.collection.Person;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonService {

    String save(Person person);

    List<Person> getPersonStartsWith(String name);

    void deletePerson(String id);

    List<Person> getPersonByAge(Integer minAge, Integer maxAge);

    Page<Person> searchPerson(String name, Integer minAge, Integer maxAge, String city, Pageable pageable);

    List<Document> getOldestPersonByCity();
}
