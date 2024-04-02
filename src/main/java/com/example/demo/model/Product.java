package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name="Product")
public class Product implements Serializable {

	
	@Id
	@SequenceGenerator(name="PRODUCT_ID_GENERATOR", sequenceName="PRODUCT_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRODUCT_ID_GENERATOR")
	private int productId;
	@Column(name="name")
	private String name;
	private int price;
	private String description;
	private int lager;

	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name="brand_id")
	private Brand brand;

	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name="category_id")
	private Category category;

	
	public Product() {
		
	}
	
	
	public Product(int product_id, String product_name, int product_price, String description, int lager,
			Brand brand, Category category) {
		super();
		this.productId = product_id;
		this.name = product_name;
		this.price = product_price;
		this.description = description;
		this.lager = lager;
		this.brand = brand;
		this.category = category;
	}

	
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLager() {
		return lager;
	}

	public void setLager(int lager) {
		this.lager = lager;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Brand getBrand() {
		return brand;
	}
	
	public void setBrand(Brand brand) {
		this.brand = brand;
	}


	
}
