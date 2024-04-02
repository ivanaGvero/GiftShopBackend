package com.example.demo.controller;

import java.util.Collection;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;
	
	  @GetMapping("/product")
	    public Collection<Product> getAllProduct(){
			return productRepository.findAll();
	  }
	  
	  @GetMapping("/product/{id}")
	    public Product getProduct(@PathVariable("id") Integer id) {
		     Optional<Product> optionalProduct = productRepository.findById(id);
		     if (optionalProduct.isPresent()) {
		         return optionalProduct.get();
		     } else {
		         throw new ResourceNotFoundException("Ne postoji proizvod sa id: " + id);
		     }
		  
		 }
	  
	  @GetMapping("/productStreet")
		public Collection<Product> getProductByProductName(@RequestParam(required=true) String product_name) {
			return productRepository.findProductByNameIgnoreCase(product_name);
		}
	  
	  @PostMapping("/product")
		public ResponseEntity<Product> createProduct(@RequestBody Product product) {
			if(!productRepository.existsById(product.getProductId())) {
				productRepository.save(product);
				return new ResponseEntity<Product>(HttpStatus.OK);
			}
			return new ResponseEntity<Product>(HttpStatus.CONFLICT);
		}
	  
	  @PutMapping("/product/{id}")
		public ResponseEntity<Product> updateProduct(@PathVariable("id") int id, @RequestBody Product newProduct) {		
		  Product product = productRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Ne postoji proizvod sa id: " + id));
			product.setName(newProduct.getName());
			product.setPrice(newProduct.getPrice());
			product.setDescription(newProduct.getDescription());
			product.setLager(newProduct.getLager());
			product.setBrand(newProduct.getBrand());
			product.setCategory(newProduct.getCategory());
			Product updatedProduct = productRepository.save(product);
			
			return ResponseEntity.ok(updatedProduct);
		}
	   
	  @DeleteMapping("/product/{id}")
		public ResponseEntity<Product> deleteProduct(@PathVariable("id") int id){
			if (!productRepository.existsById(id)) {
				return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
			}
			
			productRepository.deleteById(id);		
			return new ResponseEntity<Product>(HttpStatus.OK);
		}
	  
	  
}


