package com.financeiro.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.financeiro.model.State;
import com.financeiro.repository.StateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/states")
public class StateResource {

    @Autowired
    private StateRepository stateRepository;

    @PostMapping()
    public ResponseEntity<State> createState(@RequestParam State newState, HttpServletResponse response) {
        State state = stateRepository.save(newState);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(state.getId()).toUri();

        response.setHeader("Location", uri.toASCIIString());
        return ResponseEntity.created(uri).body(state);
    }

    @GetMapping()
    public ResponseEntity<List<State>> findAllState() {
        List<State> states = stateRepository.findAll();
        return !states.isEmpty() ? ResponseEntity.ok(states) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<State>> findOneState(@PathVariable Long id) {
        Optional<State> state = stateRepository.findById(id);
        return state.isPresent() ? ResponseEntity.ok(state) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteState(@PathVariable Long id) {
        stateRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    
}