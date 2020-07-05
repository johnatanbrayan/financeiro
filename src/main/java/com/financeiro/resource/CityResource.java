package com.financeiro.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.financeiro.model.City;
import com.financeiro.repository.CityRepository;

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
@RequestMapping("/cities")
public class CityResource {

    @Autowired
    private CityRepository cityRepository;
    
    @PostMapping()
    public ResponseEntity<City> createCity(@RequestBody City newCity, HttpServletResponse response) {
        City city = cityRepository.save(newCity);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(city.getId()).toUri();

        response.setHeader("Location", uri.toASCIIString());
        return ResponseEntity.created(uri).body(city);
    }

    @GetMapping()
    public ResponseEntity<List<City>> findAllCity() {
        List<City> cities = cityRepository.findAll();
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<City>> findOneCity(@PathVariable Long id) {
        Optional<City> city = cityRepository.findById(id);
        return city.isPresent() ? ResponseEntity.ok(city) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOneCity(@PathVariable Long id) {
        cityRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}