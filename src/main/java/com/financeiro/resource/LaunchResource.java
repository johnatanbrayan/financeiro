package com.financeiro.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.financeiro.model.Launch;
import com.financeiro.model.Person;
import com.financeiro.repository.LaunchRepository;
import com.financeiro.repository.PersonRepository;
import com.financeiro.service.exception.InvalidPersonException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // Test to execute search in the same endpoint

    // @GetMapping()
    // public ResponseEntity<List<Launch>> findAllPageLaunch(Pageable pageable, String description) {
    //     Page<Launch> searchDescription = launchRepository.findLaunchBySearch(description,pageable);
    //     Page<Launch> page = launchRepository.findAll(pageable);

    //     Page<Launch> finalPage;

    //     if (description == null) {
    //         finalPage = page;
    //     } else {
    //         finalPage = searchDescription;
    //     }
        
    //     return !finalPage.isEmpty() ? ResponseEntity.ok().body(page.getContent()) : ResponseEntity.notFound().build();
    // }

    @GetMapping()
    public ResponseEntity<List<Launch>> findAllPageLaunch(Pageable pageable) {
        Page<Launch> page = launchRepository.findAll(pageable);

        return !page.isEmpty() ? ResponseEntity.ok().body(page.getContent()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Launch>> findLaunchBySearch(@RequestParam String description, Pageable pageable) {
        Page<Launch> page = launchRepository.findLaunchBySearch(description, pageable);
        return ResponseEntity.ok().body(page.getContent());
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