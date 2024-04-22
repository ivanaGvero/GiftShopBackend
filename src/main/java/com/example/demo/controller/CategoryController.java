package com.example.demo.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/category")
    public Collection<Category> getAllCategory(){
		return categoryRepository.findAll();}
    
    @GetMapping("/category/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable("id") int id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Ne postoji kategorija sa id: " + id));
		return ResponseEntity.ok(category);
	}

    @PostMapping("/category")
	public ResponseEntity<Category> createCategory(@RequestBody Category category) {
		if(!categoryRepository.existsById(category.getCategoryId())) {
			categoryRepository.save(category);
			return new ResponseEntity<Category>(HttpStatus.OK);
		}
		return new ResponseEntity<Category>(HttpStatus.CONFLICT);
	}

    @PutMapping("/category/{id}")
	public ResponseEntity<Category> updateCategory(@PathVariable("id") int id, @RequestBody Category newCategory) {		
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Ne postoji kategorija sa id: " + id));
		category.setCategoryName(newCategory.getCategoryName());

		Category updatedCategory = categoryRepository.save(category);
		
		return ResponseEntity.ok(updatedCategory);
	}

    @DeleteMapping("/category/{id}")
	public ResponseEntity<Category> deleteCategory(@PathVariable("id") int id){
		if (!categoryRepository.existsById(id)) {
			return new ResponseEntity<Category>(HttpStatus.NO_CONTENT);
		}
		
		categoryRepository.deleteById(id);		
		return new ResponseEntity<Category>(HttpStatus.OK);
	}
}
