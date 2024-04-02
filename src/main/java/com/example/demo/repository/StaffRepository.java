package com.example.demo.repository;

import java.util.Collection;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Staff;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer>, PagingAndSortingRepository <Staff, Integer> {

	Collection<Staff> findStaffByUserNameIgnoreCase(String name);

	Staff findByUserEmail(String email);
}
