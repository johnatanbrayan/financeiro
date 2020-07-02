package com.financeiro.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.financeiro.model.Categoria;
import com.financeiro.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Create one Categoria
    @PostMapping()
    public ResponseEntity<Categoria> createCategoria(@RequestBody Categoria newCategoria, HttpServletResponse response) {
        Categoria categoria = categoriaRepository.save(newCategoria);

        // Get the url location on api created
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(categoria.getId()).toUri();
        
        // Show on header of api the location on api. Ex: localhost:8080/categorias/1
        response.setHeader("Location", uri.toASCIIString());
        return ResponseEntity.created(uri).body(categoria);
    }
    
    // List Categoria
    @GetMapping()
    public ResponseEntity<List<Categoria>> findAllCategoria() {
        List<Categoria> categoria = categoriaRepository.findAll();
        return ResponseEntity.ok(categoria);
    }

    // Return one Categoria
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Categoria>> findCategoria(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.isPresent() ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
    }

    // Update a Categoria 
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategoria(@RequestBody Categoria newCategoria) {
        categoriaRepository.save(newCategoria);
        return ResponseEntity.noContent().build();
    }

    // Delete a Categoria
    @DeleteMapping("/{id}")
    public void deleteCategoria(@PathVariable Long id) {
        categoriaRepository.deleteById(id);
    }
}