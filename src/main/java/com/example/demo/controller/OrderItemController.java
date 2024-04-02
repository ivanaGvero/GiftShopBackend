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
import com.example.demo.model.OrderItem;
import com.example.demo.repository.OrderItemRepository;

@CrossOrigin
@RestController
@RequestMapping("/order_item")
public class OrderItemController {

	  @Autowired
	  private OrderItemRepository order_itemRepository;
	  
	  @GetMapping("/order_item")
	    public Collection<OrderItem> getAllOrder_item(){
			return order_itemRepository.findAll();}
	   
	    @GetMapping("/order_item/{id}")
	    public OrderItem getOrder_item(@PathVariable("id") Integer id) {
		     Optional<OrderItem> optionalOrder_item = order_itemRepository.findById(id);
		     if (optionalOrder_item.isPresent()) {
		         return optionalOrder_item.get();
		     } else {
		         throw new ResourceNotFoundException("Ne postoji stavka porudzbine sa id: " + id);
		     }
	    }   
	    @PostMapping("/order_item")
		public ResponseEntity<OrderItem> createOrder_item(@RequestBody OrderItem order_item) {
			if(!order_itemRepository.existsById(order_item.getOrderItemId())) {
				order_itemRepository.save(order_item);
				return new ResponseEntity<OrderItem>(HttpStatus.OK);
			}
			return new ResponseEntity<OrderItem>(HttpStatus.CONFLICT);
		}
		
		 @PutMapping("/order_item/{id}")
			public ResponseEntity<OrderItem> updateOrder_item(@PathVariable("id") int id, @RequestBody OrderItem newOrder_item) {
				OrderItem order_item = order_itemRepository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException("Ne postoji stavka porudzbine sa id: " + id));
				order_item.setQuantity(newOrder_item.getQuantity());
				
				OrderItem updateOrder_item = order_itemRepository.save(order_item);
				
				return ResponseEntity.ok(updateOrder_item);
			}
		
		 @DeleteMapping("/order_item/{id}")
			public ResponseEntity<OrderItem> deleteOrder_item(@PathVariable("id") int id){
				if (!order_itemRepository.existsById(id)) {
					return new ResponseEntity<OrderItem>(HttpStatus.NO_CONTENT);
				}
				
				order_itemRepository.deleteById(id);		
				return new ResponseEntity<OrderItem>(HttpStatus.OK);
			}	
		
}
