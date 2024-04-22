package com.example.demo.controller;

import java.util.Collection;
import java.util.Optional;

import com.example.demo.stripe.StripeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Payment;
import com.example.demo.repository.PaymentRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
    private PaymentRepository paymentRepository;

	@Autowired
	private StripeClient stripeClient;
	
	@GetMapping("/payment")
    public Collection<Payment> getAllPayment(){
		return paymentRepository.findAll();}
    

    @GetMapping("/payment/{id}")
    public Payment getPayment(@PathVariable("id") Integer id) {
	     Optional<Payment> optionalPayment = paymentRepository.findById(id);
	     if (optionalPayment.isPresent()) {
	         return optionalPayment.get();
	     } else {
	         throw new ResourceNotFoundException("Ne postoji placanje sa id: " + id);
	     }
	 }
 
   /* @GetMapping("/paymentOrders")
	public Collection<Payment> getAddressByStreet(@RequestParam(required=true) String street) {
		return paymentRepository.findPaymentByOrdersIgnoreCase(orders);
	} */
    
    @PostMapping("/payment")
	public ResponseEntity<Payment> createPayment(@RequestBody Payment payment,
												 @RequestHeader(value="token", required = false) String token,
												 @RequestHeader(value="amount", required = false) Double amount,
												 @RequestHeader(value="userId", required = false) String userId) {
		if(paymentRepository.existsById(payment.getPaymentId())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			if (token != null) {
				var charge = this.stripeClient.chargeNewCard(token, amount, userId);
			}
			paymentRepository.save(payment);
			return new ResponseEntity<Payment>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Payment>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    
    @PutMapping("/payment/{id}")
	public ResponseEntity<Payment> updatePayment(@PathVariable("id") int id, @RequestBody Payment newPayment) {		
    	Payment payment = paymentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Ne postoji placanje sa id: " + id));
		payment.setPaymentMethod(newPayment.getPaymentMethod());
		Payment updatedPayment = paymentRepository.save(payment);
		
		return ResponseEntity.ok(updatedPayment);
	}
    
    @DeleteMapping("/payment/{id}")
   	public ResponseEntity<Payment> deletePayment(@PathVariable("id") int id){
   		if (!paymentRepository.existsById(id)) {
   			return new ResponseEntity<Payment>(HttpStatus.NO_CONTENT);
   		}
   		
   		paymentRepository.deleteById(id);		
   		return new ResponseEntity<Payment>(HttpStatus.OK);
   	}
}
