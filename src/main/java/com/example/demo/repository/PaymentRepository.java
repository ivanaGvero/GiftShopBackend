package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Order;
import com.example.demo.model.Payment;
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>,PagingAndSortingRepository<Payment, Integer> {

	Payment findPaymentByOrder(Order order);
}
