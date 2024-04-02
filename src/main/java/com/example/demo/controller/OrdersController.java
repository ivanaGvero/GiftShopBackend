package com.example.demo.controller;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Order;
import com.example.demo.repository.OrdersRepository;

@CrossOrigin
@RestController
@RequestMapping("/orders")
public class OrdersController {


    @Autowired
    private OrdersRepository ordersRepository;
    
    @GetMapping("/orders")
    public Collection<Order> getAllOrders(){
		return ordersRepository.findAll();}
    
    @GetMapping("/orders/{id}")
    public Order getOrders(@PathVariable("id") Integer id) {
	     Optional<Order> optionalOrders = ordersRepository.findById(id);
	     if (optionalOrders.isPresent()) {
	         return optionalOrders.get();
	     } else {
	         throw new ResourceNotFoundException("Ne postoji porudzbina sa id: " + id);
	     }
	 }
    @PostMapping("/orders")
	public ResponseEntity<Order> createOrders(@RequestBody Order orders) {
		if(!ordersRepository.existsById(orders.getOrderId())) {
			ordersRepository.save(orders);
			return new ResponseEntity<Order>(HttpStatus.OK);
		}
		return new ResponseEntity<Order>(HttpStatus.CONFLICT);
	}
    
    @PutMapping("/orders/{id}")
   	public ResponseEntity<Order> updateOrders(@PathVariable("id") int id, @RequestBody Order newOrders) {
    	Order orders = ordersRepository.findById(id)
   				.orElseThrow(() -> new ResourceNotFoundException("Ne postoji porudzbina sa id: " + id));
   		orders.setOrderDate(newOrders.getOrderDate());
   		orders.setOrderStatus(newOrders.getOrderStatus());
   		orders.setOrderPrice(newOrders.getOrderPrice());
   		
   		Order updatedOrders = ordersRepository.save(orders);
   		
   		return ResponseEntity.ok(updatedOrders);
   	}
    
    @DeleteMapping("/orders/{id}")
   	public ResponseEntity<Order> deleteOrders(@PathVariable("id") int id){
   		if (!ordersRepository.existsById(id)) {
   			return new ResponseEntity<Order>(HttpStatus.NO_CONTENT);
   		}
   		
   		ordersRepository.deleteById(id);		
   		return new ResponseEntity<Order>(HttpStatus.OK);
   	}
}
