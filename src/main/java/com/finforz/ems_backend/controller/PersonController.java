package com.finforz.ems_backend.controller;

import com.finforz.ems_backend.collection.Person;
import com.finforz.ems_backend.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/persons")
@Tag(name = "User Registration Mongo")
public class PersonController {

    @Autowired
    private PersonService personService;

    // Build Save Person Rest API
    @PostMapping("/savePerson")
    @Operation(summary = "Save User", description = "Save User")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "Unauthorized Access"),
            @ApiResponse(responseCode = "404", description = "Entity not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected Internal Exception") })

    public ResponseEntity<String> savePerson(@RequestBody Person person) {
        log.info("Saving person: {}", person);
        String personId = personService.save(person);
        return ResponseEntity.ok("Person saved successfully with id: " + personId);
    }
    @GetMapping("/getPersonNameStartsWith")
    @Operation(summary = "Get User", description = "Get User")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "Unauthorized Access"),
            @ApiResponse(responseCode = "404", description = "Entity not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected Internal Exception") })

    public ResponseEntity<?> getPersonStartsWith(@RequestParam("name") String name) {
        List<Person> persons = personService.getPersonStartsWith(name);
        return ResponseEntity.ok(persons);

    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete User", description = "Delete User")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "Unauthorized Access"),
            @ApiResponse(responseCode = "404", description = "Entity not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected Internal Exception") })

    public void deletePerson(@PathVariable("id") String id) {
        log.info("Deleting person with id: {}", id);
        personService.deletePerson(id);
    }
    // Get the list of person based on age
    @GetMapping("/age")
    @Operation(summary = "Get User Age", description = "Get User Age")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "Unauthorized Access"),
            @ApiResponse(responseCode = "404", description = "Entity not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected Internal Exception") })

    public List<Person> getPersonByAge(
            @RequestParam Integer minAge, @RequestParam Integer maxAge
    ) {
        return personService.getPersonByAge(minAge, maxAge);
    }
    // Implementation of pagination along with mongo template instead of using mongo repository
    @GetMapping("/search")
    @Operation(summary = "Search User", description = "Search User")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "Unauthorized Access"),
            @ApiResponse(responseCode = "404", description = "Entity not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected Internal Exception") })

    public Page<Person> searchPerson(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return personService.searchPerson(name, minAge, maxAge, city, pageable);
    }
    @GetMapping("/oldestPerson")
    @Operation(summary = "Get Oldest User", description = "Get Oldest User")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "Unauthorized Access"),
            @ApiResponse(responseCode = "404", description = "Entity not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected Internal Exception") })

    public List<Document> getOldestPerson() {
        return personService.getOldestPersonByCity();
    }
}
