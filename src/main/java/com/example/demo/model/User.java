package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import java.util.Collection;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="Users")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;

	@NotEmpty(message = "Name is required")
	private String name;

	@NotEmpty(message = "Surname is required")
	private String surname;

	@Email(message = "Nevažeća email adresa")
	@Column(unique = true)
	private String email;
	@Column(name = "user_name", unique = true)
	private String username;

	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}$",
			message = "Lozinka mora sadržavati najmanje 6 karaktera, jedno veliko slovo i jedan broj")
	private String password;

	@Enumerated(EnumType.STRING)
	private ERole role;

	@OneToOne(mappedBy = "user")
	@JsonIgnore
	private Customer customer;
	
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

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(getRole().name()));
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
