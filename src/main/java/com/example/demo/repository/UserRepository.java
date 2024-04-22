package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface UserRepository  extends JpaRepository<User, Integer>, PagingAndSortingRepository <User, Integer> {

	Optional<User> findUserByEmail(String email);
	Optional<User> findUserByUsername(String username);
	Boolean existsByEmail(String email);
	Boolean existsByUsername(String username);
}
