package com.example.demo.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="Staff")
public class Staff {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String position;

	@OneToOne
	@JoinColumn(name = "userId")
	private User user;
	
	public Staff() {
	}
	
	public Staff(String position) {
		this.position = position;
	}

	public Staff(User user, String position) {
		this.position = position;
	}

	public Staff(User user, ERole role, String position) {
		this.user = user;
		user.setRole(role);
		this.position = position;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
