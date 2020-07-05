package com.financeiro.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.financeiro.model.Person;
import com.financeiro.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/persons")
public class PersonResource {
 
    @Autowired
    private PersonRepository personRepository;

    @PostMapping()
    public ResponseEntity<Person> createPerson(@RequestBody Person newPerson, HttpServletResponse response) {
        Person person = personRepository.save(newPerson);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(person.getId()).toUri();
        response.setHeader("Location", uri.toASCIIString());
        return ResponseEntity.created(uri).body(person);    
    }

    @GetMapping()
    public ResponseEntity<List<Person>> findAllPerson() {
        List<Person> persons = personRepository.findAll();
        return !persons.isEmpty() ? ResponseEntity.ok(persons) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Person>> findOnePerson(@PathVariable Long id) {
        Optional<Person> personal = personRepository.findById(id);

        return personal.isPresent() ? ResponseEntity.ok(personal) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}