package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Order;
@Repository
public interface OrdersRepository extends JpaRepository<Order, Integer>, PagingAndSortingRepository<Order, Integer> {

}
