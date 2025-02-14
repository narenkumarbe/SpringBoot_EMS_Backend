package com.finforz.ems_backend.service;

import com.finforz.ems_backend.collection.Person;
import com.finforz.ems_backend.exception.ResourceNotFoundException;
import com.finforz.ems_backend.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public String save(Person person) {
        return personRepository.save(person).getPersonId();
    }

    @Override
    public List<Person> getPersonStartsWith(String name) {
        List<Person> personList = personRepository.findByFirstNameStartsWith(name);
        if(personList.isEmpty()) {
            throw new ResourceNotFoundException("No records found for the given name: " + name);
        }
        return personList;
    }

    @Override
    public void deletePerson(String id) {
        personRepository.deleteById(id);
    }

    @Override
    public List<Person> getPersonByAge(Integer minAge, Integer maxAge) {
        return personRepository.findPersonByAgeBetween(minAge, maxAge);
    }

    @Override
    public Page<Person> searchPerson(String name, Integer minAge, Integer maxAge, String city, Pageable pageable) {
        Query query = new Query().with(pageable);
        List<Criteria> criteria = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            criteria.add(Criteria.where("firstName").regex(name, "i")); // i - ignore case sensitivity
        }
        if (minAge!=null && maxAge!=null) {
            criteria.add(Criteria.where("age").gte(minAge).lte(maxAge));
        }
        if (city!=null && !city.isEmpty()) {
            criteria.add(Criteria.where("addresses.city").regex(city, "i")); // Case-insensitive regex for city
        }
        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }
        log.info("Query: {}", query.toString());
        List<Person> personList = mongoTemplate.find(query, Person.class);
        log.info("Person List: {}", personList.toString());
        long count = mongoTemplate.count(query.skip(0).limit(0), Person.class);
        log.info("Count: {}", count);
        Page < Person > peoplePage = PageableExecutionUtils.getPage(personList,
                pageable, () -> count);
        log.info("Page: {}", peoplePage.toString());
        return peoplePage;

    }

    @Override
    public List<Document> getOldestPersonByCity() {
        //unwind operation (flattern) in mongodb, sort and group operation
        UnwindOperation unwindOperation = Aggregation.unwind("addresses");
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "age");
        GroupOperation groupOperation = Aggregation.group("addresses.city").first(Aggregation.ROOT).as("oldestPerson");
        Aggregation aggregation = Aggregation.newAggregation(unwindOperation,sortOperation,groupOperation);
        List<Document> personAggregation = mongoTemplate.aggregate(aggregation, Person.class, Document.class).getMappedResults();
        log.info("Oldest Person: {}", personAggregation.toString());
        return personAggregation;
    }
}
