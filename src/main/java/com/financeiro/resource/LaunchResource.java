package com.financeiro.resource;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.financeiro.model.Launch;
import com.financeiro.model.Person;
import com.financeiro.repository.LaunchRepository;
import com.financeiro.repository.PersonRepository;
import com.financeiro.service.exception.InvalidPersonException;
import com.financeiro.service.query.LaunchSpecification;

import static org.springframework.data.jpa.domain.Specification.where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/launches")
public class LaunchResource {

    @Autowired
    LaunchRepository launchRepository;

    @Autowired
    PersonRepository personRepository;

    @PostMapping()
    public ResponseEntity<Launch> createLaunch(@Valid @RequestBody Launch newLaunch, HttpServletResponse response) {

        Person person = personRepository.findById(newLaunch.getPerson().getId()).orElseThrow(() -> new EmptyResultDataAccessException(1));
        newLaunch.setPerson(person);

        if(newLaunch.getPerson().getStatus() == false) {
            throw new InvalidPersonException();
        }

        Launch launch =  launchRepository.save(newLaunch);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(launch.getId()).toUri();

        response.setHeader("Location", uri.toASCIIString());
        return ResponseEntity.created(uri).body(launch);
    }

    @GetMapping()
    public ResponseEntity<List<Launch>> findAllLaunch(@RequestParam(required = false) String description, @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dueDateFrom, @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dueDateUntil, Pageable pageable) {
        
        Page<Launch> page = launchRepository.findAll(pageable);

        if(description != null && dueDateFrom != null && dueDateUntil != null) {
            page = launchRepository.findAll(where(LaunchSpecification.searchByDescription(description)).and(LaunchSpecification.searchByDueDateFrom(dueDateFrom)).and(LaunchSpecification.searchByDueDateUntil(dueDateUntil)), pageable);
        } else if(description != null && dueDateFrom != null) {
            page = launchRepository.findAll(where(LaunchSpecification.searchByDescription(description)).and(LaunchSpecification.searchByDueDateFrom(dueDateFrom)), pageable);
        } else if(description != null && dueDateUntil != null) {
            page = launchRepository.findAll(where(LaunchSpecification.searchByDescription(description)).and(LaunchSpecification.searchByDueDateUntil(dueDateUntil)), pageable);
        } else if(dueDateFrom != null && dueDateUntil != null) {
            page = launchRepository.findAll(where(LaunchSpecification.searchByDueDateFrom(dueDateFrom)).and(LaunchSpecification.searchByDueDateUntil(dueDateUntil)), pageable);
        } else if(description != null) {
            page = launchRepository.findAll(where(LaunchSpecification.searchByDescription(description)), pageable);
        } else if(dueDateFrom != null) {
            page = launchRepository.findAll(where(LaunchSpecification.searchByDueDateFrom(dueDateFrom)), pageable);
        } else if(dueDateUntil != null) {
            page = launchRepository.findAll(where(LaunchSpecification.searchByDueDateUntil(dueDateUntil)), pageable);
        }

        return !page.isEmpty() ? ResponseEntity.ok().body(page.getContent()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Launch>> findOneLaunch(@PathVariable Long id) {
        Optional<Launch> launch = launchRepository.findById(id);
        return launch.isPresent() ? ResponseEntity.ok(launch) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Launch> updateLaunch(@Valid @RequestBody Launch launch, @PathVariable Long id) {
        launch.setId(id);
        launchRepository.save(launch);
        return ResponseEntity.ok(launch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOneLaunch(@PathVariable Long id) {
        launchRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}