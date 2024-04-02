package com.example.demo.controller;


import java.util.Collection;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Address;
import com.example.demo.repository.AddressRepository;

@CrossOrigin
@RestController
@RequestMapping("/address")
public class AddressController {
    private final AddressRepository addressRepository;

	public AddressController(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}

	@GetMapping("/address")
    public Collection<Address> getAllAddress(){
		return addressRepository.findAll();}
    

    @GetMapping("/address/{id}")
    public Address getAddress(@PathVariable("id") Integer id) {
	     Optional<Address> optionalAddress = addressRepository.findById(id);
	     if (optionalAddress.isPresent()) {
	         return optionalAddress.get();
	     } else {
	         throw new ResourceNotFoundException("Ne postoji adresa sa id: " + id);
	     }
	 }

    @GetMapping("/addressStreet")
	public Collection<Address> getAddressByStreet(@RequestParam(required=true) String street) {
		return addressRepository.findAddressByStreetIgnoreCase(street);
	}
    
    @PostMapping("/address")
	public ResponseEntity<Address> createAddress(@RequestBody Address address) {
		if(!addressRepository.existsById(address.getAddressId())) {
			addressRepository.save(address);
			return new ResponseEntity<Address>(HttpStatus.OK);
		}
		return new ResponseEntity<Address>(HttpStatus.CONFLICT);
	}
    
    @PutMapping("/address/{id}")
	public ResponseEntity<Address> updateAddress(@PathVariable("id") int id, @RequestBody Address newAddress) {		
		Address address = addressRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Ne postoji adresa sa id: " + id));
		address.setStreet(newAddress.getStreet());
		address.setCountry(newAddress.getCountry());
		address.setCity(newAddress.getCity());
		address.setZip(newAddress.getZip());
		
		Address updatedAddress = addressRepository.save(address);
		
		return ResponseEntity.ok(updatedAddress);
	}
    
    @DeleteMapping("/address/{id}")
	public ResponseEntity<Address> deleteAddress(@PathVariable("id") int id){
		if (!addressRepository.existsById(id)) {
			return new ResponseEntity<Address>(HttpStatus.NO_CONTENT);
		}
		
		addressRepository.deleteById(id);		
		return new ResponseEntity<Address>(HttpStatus.OK);
	}
	
}

