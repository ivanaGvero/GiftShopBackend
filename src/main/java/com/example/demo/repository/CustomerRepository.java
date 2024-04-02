package com.example.demo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Customer;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>,PagingAndSortingRepository<Customer, Integer> {

	Collection<Customer> findCustomerByUserNameIgnoreCase(String name);

	Customer findByUserEmail(String email);

	
}
