package com.example.demo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name="Brand")
public class Brand {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int brandId;
	@Column(name="brand_name")
	private String brandName;
	
	@OneToMany(mappedBy="brand", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonIgnore
	private List<Product> products = new ArrayList<>();
	
	

	public Brand() {
		
	}

	public Brand(int id_brand, String brand_name, List<Product> products) {
		super();
		this.brandId = id_brand;
		this.brandName = brand_name;
		this.products = products;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brand_name) {
		this.brandName = brand_name;
	}

	@JsonIgnore
	public List<Product> getProduct() {
		return products;
	}

	@JsonIgnore
	public void setProduct(List<Product> products) {
		this.products = products;
	}

	
	
	public Product addProizvod(Product product) {
		getProduct().add(product);
		product.setBrand(this);

		return product;
	}

	public Product removeProizvod(Product product) {
		getProduct().remove(product);
		product.setBrand(null);

		return product;
	}
	
	
}
