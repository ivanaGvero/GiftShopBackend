package com.example.demo.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="Customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String phone;
	private String address;

	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "userId")
	private User user;

	public Customer() {
		super();
	}

	public Customer(User user) {
		this.user = user;
	}
	
	public Customer(User user, String address, String phone) {
		this.user = user;
		this.address=address;
		this.phone=phone;
	}
	
	public Customer(User user, ERole role, String address, String phone) {
		this.user = user;
		this.user.setRole(role);
		this.address=address;
		this.phone=phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
