package com.financeiro.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.financeiro.model.Address;
import com.financeiro.repository.AddressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/adresses")
public class AddressResource {

    @Autowired
    private AddressRepository addressRepository;

    // Create one Address
    @PostMapping()
    public ResponseEntity<Address> createAddress(@RequestBody Address newAddress, HttpServletResponse response) {
        Address address = addressRepository.save(newAddress);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(address.getId()).toUri();

        response.setHeader("Location", uri.toASCIIString());
        return ResponseEntity.created(uri).body(address);
    }

    // Find all Address
    @GetMapping()
    public ResponseEntity<List<Address>> findAllAddress() {
        List<Address> adresses = addressRepository.findAll(); 
        return !adresses.isEmpty() ? ResponseEntity.ok(adresses) : ResponseEntity.notFound().build();
    }

    // Return one Address
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Address>> findOneAddress(@PathVariable Long id) {
        Optional<Address> address = addressRepository.findById(id);
        return address.isPresent()  ? ResponseEntity.ok(address) : ResponseEntity.notFound().build();
    }

    // Update Address
    @PutMapping("/{id}") 
    public ResponseEntity<Address> updateAddress(@Valid @RequestBody Address address, @PathVariable Long id) {
        addressRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));

        address.setId(id);
        addressRepository.save(address);
        return ResponseEntity.ok(address);
    }

    // Delete Address
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOneAddress(@PathVariable Long id) {
        addressRepository.deleteById(id);   
        return ResponseEntity.noContent().build();
    }
    
}