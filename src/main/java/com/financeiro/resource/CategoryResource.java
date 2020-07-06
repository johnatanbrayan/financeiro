package com.financeiro.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.financeiro.model.Category;
import com.financeiro.repository.CategoryRepository;

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
@RequestMapping("/categories")
public class CategoryResource {

    @Autowired
    private CategoryRepository categoryRepository;

    // Create one Category
    @PostMapping()
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category newCategory, HttpServletResponse response) {
        Category category = categoryRepository.save(newCategory);

        // Get the url location on api created
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(category.getId()).toUri();
        
        // Show on header of api the location on api. Ex: localhost:8080/categories/1
        response.setHeader("Location", uri.toASCIIString());
        return ResponseEntity.created(uri).body(category);
    }
    
    // List Category
    @GetMapping()
    public ResponseEntity<List<Category>> findAllCategory() {
        List<Category> category = categoryRepository.findAll();
        return !category.isEmpty() ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }

    // Return one Category
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Category>> findCategory(@PathVariable Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.isPresent() ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }

    // Update a Category 
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@RequestBody Category newCategory) {
        categoryRepository.save(newCategory);
        return ResponseEntity.noContent().build();
    }

    // Delete a Category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}