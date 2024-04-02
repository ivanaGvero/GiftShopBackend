package com.example.demo.repository;


import java.util.Collection;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>,PagingAndSortingRepository<Product, Integer>{

	Collection<Product> findProductByNameIgnoreCase(String productName);

}
