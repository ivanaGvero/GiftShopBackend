package com.example.demo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>,PagingAndSortingRepository<Address, Integer> {

	Collection<Address> findAddressByStreetIgnoreCase(String street);

	
}
