package com.finforz.ems_backend.repository;

import com.finforz.ems_backend.collection.Person;
import com.finforz.ems_backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}


