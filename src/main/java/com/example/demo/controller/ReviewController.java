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
import com.example.demo.model.Review;
import com.example.demo.repository.ReviewRepository;



@CrossOrigin
@RestController
@RequestMapping("/review")
public class ReviewController {

	 @Autowired
	    private ReviewRepository reviewRepository;
	 
	 @GetMapping("/review")
	    public Collection<Review> getAllReview(){
			return reviewRepository.findAll();} 
	 
	 @GetMapping("/review/{id}")
	    public Review getReview(@PathVariable("id") Integer id) {
		     Optional<Review> optionalReview = reviewRepository.findById(id);
		     if (optionalReview.isPresent()) {
		         return optionalReview.get();
		     } else {
		         throw new ResourceNotFoundException("Ne postoji recenzija sa id: " + id);
		     }
		 }
	 @PostMapping("/review")
		public ResponseEntity<Review> createReview(@RequestBody Review review) {
			if(!reviewRepository.existsById(review.getReviewId())) {
				reviewRepository.save(review);
				return new ResponseEntity<Review>(HttpStatus.OK);
			}
			return new ResponseEntity<Review>(HttpStatus.CONFLICT);
		}
	 
	 @PutMapping("/review/{id}")
		public ResponseEntity<Review> updateReview(@PathVariable("id") int id, @RequestBody Review newReview) {		
		 Review review = reviewRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Ne postoji recenzija sa id: " + id));
			review.setRating(newReview.getRating());
			review.setComment(newReview.getComment());
			
			Review updatedReview = reviewRepository.save(review);
			
			return ResponseEntity.ok(updatedReview);
		}
	 
	 @DeleteMapping("/review/{id}")
		public ResponseEntity<Review> deleteReview(@PathVariable("id") int id){
			if (!reviewRepository.existsById(id)) {
				return new ResponseEntity<Review>(HttpStatus.NO_CONTENT);
			}
			
			reviewRepository.deleteById(id);		
			return new ResponseEntity<Review>(HttpStatus.OK);
		}
	 
}
