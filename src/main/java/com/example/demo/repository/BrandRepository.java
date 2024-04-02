package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer>,PagingAndSortingRepository<Brand, Integer>  {

	Brand findBrandByBrandNameIgnoreCase(String brandName);
}
