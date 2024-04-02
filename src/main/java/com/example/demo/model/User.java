package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="Users")
public class User {

	@Id
	@SequenceGenerator(name="USER_ID_GENERATOR", sequenceName="USERS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_ID_GENERATOR")
	private int userId;
	private String name;
	private String surname;

	private String email;
	@Column(name = "user_name")
	private String username;

	private String password;

	@Enumerated(EnumType.STRING)
	private ERole role;
	
	
	
	public User() {
		
	}


	public User(int userId, String name, String surname, String email,  String user_name,
			String password, ERole role) {
		super();
		this.userId = userId;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.username = user_name;
		this.password = password;
		this.role = role;
	}

	public User(  String email, String user_name,
			 String password) {
		
		this.email = email;
		this.username = user_name;
		this.password = password;
		
	}

	public User(User user) {
		this.name=user.name;
		this.surname = user.surname;
		this.email = user.email;
		this.username = user.username;
		this.password = user.password;
		this.role = user.role;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}



	public ERole getRole() {
		return role;
	}


	public void setRole(ERole role) {
		this.role = role;
	}
	
	
}
