package com.finforz.ems_backend.repository;

import com.finforz.ems_backend.collection.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends MongoRepository<Person, String> {

    List<Person> findByFirstNameStartsWith(String name);

//    List<Person> findByAgeBetween(Integer minAge, Integer maxAge); // <37,4>-(43,5)
    @Query(value = "{ 'age' : { $gte: ?0, $lte: ?1 } }", fields = "{addresses : 0}")
    List<Person> findPersonByAgeBetween(Integer minAge, Integer maxAge);
}
