package com.example.demo.controller;

import com.example.demo.model.StripePayment;
import com.example.demo.repository.StripePaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/stripe")
public class StripePaymentsController {

    @Autowired
    private StripePaymentRepository repository;

    @GetMapping("/payment")
    public Collection<StripePayment> getAllPayment(){
        return repository.findAll();
    }
}
