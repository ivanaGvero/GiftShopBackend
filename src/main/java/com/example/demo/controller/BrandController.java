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
import com.example.demo.model.Brand;
import com.example.demo.repository.BrandRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/brand")
public class BrandController {

	@Autowired
	private BrandRepository brandRepository;

	@GetMapping("/brand")
	public Collection<Brand> getAllBrand(){
		return brandRepository.findAll();}

	@GetMapping("/brand/{id}")
	public ResponseEntity<Brand> getBrandById(@PathVariable("id") int id) {
		Brand brand = brandRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + id));
		return ResponseEntity.ok(brand);
	}


	@PostMapping("/brand")
	public ResponseEntity<Brand> createBrand(@RequestBody Brand brand) {
		if(!brandRepository.existsById(brand.getBrandId())) {
			brandRepository.save(brand);
			return new ResponseEntity<Brand>(HttpStatus.OK);
		}
		return new ResponseEntity<Brand>(HttpStatus.CONFLICT);
	}

	@PutMapping("/brand/{id}")
	public ResponseEntity<Brand> updateBrand(@PathVariable("id") int id, @RequestBody Brand newBrand) {
		Brand brand = brandRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + id));
		brand.setBrandName(newBrand.getBrandName());
		Brand updatedBrand = brandRepository.save(brand);

		return ResponseEntity.ok(updatedBrand);
	}

	@DeleteMapping("/brand/{id}")
	public ResponseEntity<Brand> deleteBrand(@PathVariable("id") int id){
		if (!brandRepository.existsById(id)) {
			return new ResponseEntity<Brand>(HttpStatus.NO_CONTENT);
		}

		brandRepository.deleteById(id);
		return new ResponseEntity<Brand>(HttpStatus.OK);
	}

}


