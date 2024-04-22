package com.example.demo.controller;

import java.util.Collection;
import java.util.Optional;

import com.example.demo.model.Customer;
import com.example.demo.model.ERole;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Staff;
import com.example.demo.repository.StaffRepository;

@CrossOrigin
@RestController
@RequestMapping("/staff")
public class StaffController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	 @Autowired
	    private StaffRepository staffRepository;
	 
	 @GetMapping("/staff") 
	    public Collection<Staff> getAllStaff(){
	    	return staffRepository.findAll();
	    }
	 @GetMapping("/staff/{id}")
	    public Staff getStaff(@PathVariable("id") Integer id) {
		     Optional<Staff> optionalStaff = staffRepository.findById(id);
		     if (optionalStaff.isPresent()) {
		         return optionalStaff.get();
		     } else {
		         throw new ResourceNotFoundException("Ne postoji radnik sa id: " + id);
		     }
		 }
	@PostMapping("/staff")
	public ResponseEntity<Staff> postUser(@RequestBody Staff staff){
		if (!staffRepository.existsById(staff.getId())) {
			staff.getUser().setRole(ERole.ROLE_STAFF);
			staff.getUser().setPassword(passwordEncoder.encode(staff.getUser().getPassword()));
			staffRepository.save(staff);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}
	 @PutMapping("/staff/{id}")
	    public ResponseEntity<Staff> updateStaff(@PathVariable("id") int id, @RequestBody Staff newStaff) {		
		 Staff staff = staffRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Ne postoji radnik sa id: " + id));

		 staff.getUser().setName(newStaff.getUser().getName());
		 staff.getUser().setSurname(newStaff.getUser().getSurname());
		 staff.setPosition(newStaff.getPosition());
	        
		 Staff updatedStaff = staffRepository.save(staff);
	        return ResponseEntity.ok(updatedStaff);
	    }
	 @DeleteMapping("/staff/{id}")
	   	public ResponseEntity<Staff> deleteStaff(@PathVariable("id") int id){
	   		if (!staffRepository.existsById(id)) {
	   			return new ResponseEntity<Staff>(HttpStatus.NO_CONTENT);
	   		}
	   		
	   		staffRepository.deleteById(id);		
	   		return new ResponseEntity<Staff>(HttpStatus.OK);
	   	}
}
