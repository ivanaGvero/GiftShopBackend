package com.example.demo.controller;

import java.util.Collection;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@CrossOrigin
@RestController
public class UserController {

    private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserController(UserRepository userRepository,
						  PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/user")
	    public Collection<User> getAllUser(){
			return userRepository.findAll();}
	 
	 
	 @GetMapping("/user/{id}")
	 public User getUser(@PathVariable("id") Integer id) {
	     Optional<User> optionalUser = userRepository.findById(id);
	     if (optionalUser.isPresent()) {
	         return optionalUser.get();
	     } else {
	         throw new ResourceNotFoundException("Ne postoji korisnik sa id: " + id);
	     }
	 }

	@PostMapping("/user")
	public ResponseEntity<User> postUser(@RequestBody User user) {
		if (!userRepository.existsById(user.getUserId())) {
			if (userRepository.existsByUsername(user.getUsername())) {
				return new ResponseEntity<User>(HttpStatus.CONFLICT);
			}

			user.setPassword(passwordEncoder.encode(user.getPassword()));

			try {
				userRepository.save(user);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

			return new ResponseEntity<User>(HttpStatus.OK);
		}

		return new ResponseEntity<User>(HttpStatus.CONFLICT);
	}


	@PutMapping("/user")
		public ResponseEntity<User> putUser(@RequestBody User user) {
			if (!userRepository.existsById(user.getUserId())){
				return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
			}
			userRepository.save(user);
			return new ResponseEntity<User>(HttpStatus.OK);
		}
		
		@DeleteMapping("/user/{id}")
		public ResponseEntity<User> deleteUser(@PathVariable("id") Integer id) {
			if (!userRepository.existsById(id))
				return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
			userRepository.deleteById(id);
			
			return new ResponseEntity<User>(HttpStatus.OK);
		}

}
