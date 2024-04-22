package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.example.demo.model.Brand;
import com.example.demo.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ResourceLoader resourceLoader;
	
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
		public ResponseEntity<Product> createProduct(@RequestParam("image") MultipartFile image,
													 @RequestParam String name,
													 @RequestParam String  description,
													 @RequestParam Integer price,
													 @RequestParam Integer lager,
													 @RequestParam Integer categoryId,
													 @RequestParam Integer brandId
													 ) throws IOException {


		  // Get the absolute path to the upload directory
		  String uploadPath = System.getProperty("user.dir") + "/" + "src/main/resources/static/uploads/";

		  // Create the upload directory if it doesn't exist
		  File directory = new File(uploadPath);
		  if (!directory.exists()) {
			  directory.mkdirs();
		  }

		  // Save the image file in the upload directory
		  String fileName = StringUtils.cleanPath(UUID.randomUUID().toString() + "_" + image.getOriginalFilename());
		  Path filePath = Paths.get(uploadPath + fileName);
		  Files.copy(image.getInputStream(),
				  filePath, StandardCopyOption.REPLACE_EXISTING);

		  Product product = new Product();
		  product.setName(name);
		  product.setDescription(description);
		  product.setPrice(price);
		  product.setLager(lager);
		  product.setImage(fileName);

		  Category category = new Category();
		  category.setCategoryId(categoryId);
		  Brand brand = new Brand();
		  brand.setBrandId(brandId);

		  product.setBrand(brand);
		  product.setCategory(category);
			if(!productRepository.existsById(product.getProductId())) {
				productRepository.save(product);
				return new ResponseEntity<Product>(HttpStatus.OK);
			}
			return new ResponseEntity<Product>(HttpStatus.CONFLICT);
		}
	  
	  @PutMapping("/product/{id}")
		public ResponseEntity<Product> updateProduct(@PathVariable("id") int id,
													 @RequestParam(value = "image", required = false) MultipartFile image,
													 @RequestParam String name,
													 @RequestParam String  description,
													 @RequestParam Integer price,
													 @RequestParam Integer lager,
													 @RequestParam Integer categoryId,
													 @RequestParam Integer brandId) throws IOException {

		  Product product = productRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Ne postoji proizvod sa id: " + id));
			product.setName(name);
			product.setPrice(price);
			product.setDescription(description);
			product.setLager(lager);

		  Category category = new Category();
		  category.setCategoryId(categoryId);
		  Brand brand = new Brand();
		  brand.setBrandId(brandId);

			product.setBrand(brand);
			product.setCategory(category);

			if (image != null && !image.isEmpty()) {
				// Get the absolute path to the upload directory
				String uploadPath = System.getProperty("user.dir") + "/" + "src/main/resources/static/uploads/";

				// Create the upload directory if it doesn't exist
				File directory = new File(uploadPath);
				if (!directory.exists()) {
					directory.mkdirs();
				}

				// Save the image file in the upload directory
				String fileName;
				if (product.getImage() != null && !Objects.equals(product.getImage(), "")) {
					fileName = product.getImage();
				} else {
					fileName = StringUtils.cleanPath(UUID.randomUUID().toString() + "_" + image.getOriginalFilename());
				}
				Path filePath = Paths.get(uploadPath + fileName);
				Files.copy(image.getInputStream(),
						filePath, StandardCopyOption.REPLACE_EXISTING);

				product.setImage(fileName);

			}
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


