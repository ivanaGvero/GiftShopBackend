package com.example.demo.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="Address")
public class Address {
	@Id
	@SequenceGenerator(name="ADDRESS_ID_GENERATOR", sequenceName="ADDRESS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ADDRESS_ID_GENERATOR")
	private int addressId;
	@Column(name="street")
	private String street;
	
	@Column(name="country")
	private String country;
	
	@Column(name="city")
	private String city;
	
	@Column(name="zip")
	private int zip;
	
	@OneToMany(mappedBy="address", cascade = {CascadeType.DETACH, CascadeType.REMOVE})
	@JsonIgnore
	private List<Customer> customers ;
	
public Address() {
		
	}
	
	public Address(int addressId, String street, String country, String city, int zip, List<Customer> customers) {
		super();
		this.addressId = addressId;
		this.street = street;
		this.country = country;
		this.city = city;
		this.zip = zip;
		this.customers = customers;
	}



	public Address(Address adresa) {
		this.addressId =adresa.addressId;
		this.street=adresa.street;
		this.country=adresa.country;
		this.city=adresa.city;
		this.zip=adresa.zip;
	}

	
	
	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public int getZip() {
		return zip;
	}


	public void setZip(int zip) {
		this.zip = zip;
	}



	public List<Customer> getCustomers() {
		return customers;
	}


	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
	public Customer addCustomer(Customer customer) {
		getCustomers().add(customer);
		customer.setAddress(this);

		return customer;
	}
	

	public Customer removeCustomer(Customer customer) {
		getCustomers().remove(customer);
		customer.setAddress(null);

		return customer;
	}

}
