package com.dealer.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dealer.model.BankDetails;
import com.dealer.model.Booking;
import com.dealer.model.CropDto;
import com.dealer.model.Dealer;
import com.dealer.model.PaymentDto;
import com.dealer.model.ReceiptDto;
import com.dealer.model.ReviewDto;
import com.dealer.service.DealerService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/dealer")
@Tag(name="Dealer APIs",description="Crop Deal - Dealer Methods")
public class DealerController {
	
	@Autowired
	DealerService dealer_service;
	
		//1 get all dealers list -- admin
		@GetMapping("/allDealers")
		public ResponseEntity<List<Dealer>> getAllDealers()
		{
			return dealer_service.getAllDealer();
		}
		
		//2 delete dealer from list -- admin
		@DeleteMapping("/delete/{id}")
		public ResponseEntity<String> deleteDealer(@PathVariable int id)
		{
			return dealer_service.deleteDealerById(id);
		}
		
		//3 register/add dealer -- admin,dealer
		@PostMapping("/register")
		public ResponseEntity<String> registerDealer(@RequestBody Dealer dealer)
		{
			return dealer_service.registerDealer(dealer);
		}
		
		//4 get profile details -- admin,dealer
		@GetMapping("/profile/{id}")
		public ResponseEntity<Optional<Dealer>> getDealerProfileById(@PathVariable int id)
		{
			return dealer_service.getDealerProfileDetailsById(id);
		}
		
		//5 edit profile details -- admin ,dealer
		@PutMapping("/profile/edit/{id}")
		public ResponseEntity<String> editProfileOfDealer(@PathVariable int id,@RequestBody Dealer dealer)
		{
			return dealer_service.editDealerProfile(id, dealer);
		}
		
		//6 add bank details --dealer
		@PostMapping("/{id}/add-bank-details")
		public ResponseEntity<String> addBankDetails(@PathVariable int id, @RequestBody BankDetails bankDetails) {
		    return dealer_service.addBankDetails(id, bankDetails);
		}
		
		//7 edit/update payment details -- dealer
		@PutMapping("/{id}/update-bank-details")
		public ResponseEntity<String> updateBankDetails(@PathVariable int id, @RequestBody BankDetails newDetails) {
		    return dealer_service.updateBankDetails(id, newDetails);
		}
		
		//8 get bank details
		@GetMapping("/bankDetails/{id}")
		public ResponseEntity<BankDetails> getBankDetails(@PathVariable int id)
		{
			return dealer_service.getBankDetails(id);
		}
		
		
		
		
		//-----interaction with crop service------------
		
		@GetMapping("/crop-info/{id}")
		public ResponseEntity<Optional<CropDto>> getCropDetailsById(@PathVariable int id)
		{
			return dealer_service.getCropDetailsById(id);
		}
		

		//1 get all published crops
		@GetMapping("/getAllCrops")
		@CircuitBreaker(name = "cropService", fallbackMethod = "fallbackMethod")
		public ResponseEntity<?> getAllPubishedCrops()
		{
		
			return dealer_service.getAllPubishedCrops();
			
		}
		
		//2 book published crop
		@PostMapping("{dealer_id}/book/{crop_id}")
		public ResponseEntity<String> bookPubishedCrop(@PathVariable int dealer_id ,@PathVariable int crop_id, @RequestParam("req_quantity") double req_quantity)
		{
			return dealer_service.bookPubishedCrop(dealer_id,crop_id,req_quantity);
		}
	
		//3 cancel booked crops
		@PostMapping("{dealer_id}/cancel-book/{crop_id}")
		public ResponseEntity<String> cancelBooking(@PathVariable int dealer_id ,@PathVariable int crop_id, @RequestParam("cancel_quantity") double cancel_quantity)
		{
			return dealer_service.cancelBooking(dealer_id, crop_id,cancel_quantity);
		}
		
		//4 get crops booked by me
		@GetMapping("{dealer_id}/MyBookedCrops")
		public ResponseEntity<?> getMyBookedCrops(@PathVariable int dealer_id)
		{
			return dealer_service.getMyBookedCrops(dealer_id);
		}
		
		//5 get booking details by id
		@GetMapping("/bookInfo/{book_id}")
		public ResponseEntity<Optional<Booking>> getBookDetails(@PathVariable int book_id)
		{
			return dealer_service. getBookDetails(book_id);
		}
		
		//6 get all bookings
		@GetMapping("/all-booings")
		public ResponseEntity<List<Booking>> allBookings()
		{
			return dealer_service. allBookings();
		}
		
		
	
		//-------------interaction with crop service (review methods)----------
		
		// 5 post review
		@PostMapping("{dealer_id}/review-post/{crop_id}")
		public ResponseEntity<String> postReview(@PathVariable int dealer_id, @PathVariable int crop_id ,@RequestBody ReviewDto review)
		{
			return dealer_service.postReview(dealer_id,crop_id,review);
		}
		
		@GetMapping("/reviews/{crop_id}")
		public ResponseEntity<List<ReviewDto>> getReviewes( @PathVariable int crop_id )
		{
			return dealer_service.getReviews(crop_id);
		}
		
		//-----------------------interaction with payment service----------
		
		//1 get amount to pay
	    @GetMapping("/amount-to-pay/{book_id}")
	    public ResponseEntity<String> getAmountToBePaid(@PathVariable int book_id) {
	        return dealer_service.getAmountToBePaid(book_id);
	    }
	    
	    //2  Make payment	
	    @PostMapping("/make-payement")
	    public ResponseEntity<String> makePayment(@RequestBody PaymentDto payment) {
	    	 return dealer_service.makePayment(payment);
	     
	    }
	    
	    //3 List Of Payments done by dealers
	    @GetMapping("/my-payments")
	    public ResponseEntity<List<PaymentDto>> forDealerPayments(@RequestParam int dealer_id) {
	        return dealer_service.forDealerPayments(dealer_id);
	    }
	    
	    //4 get receipt
	    @GetMapping("/receipt/{paymentId}")
	    public ResponseEntity<ReceiptDto> getReceipt(@PathVariable Integer paymentId) {
	        return dealer_service.getReceipt(paymentId);
	    }
	    
	    
	    //-----------------------for notification service ----------------
	    //1 get list emails of all dealers
	    @GetMapping("/emails")
	    public ResponseEntity<List<String>> getAllEmails() {
	        return dealer_service.getAllEmails();
	    }
	    
	    public ResponseEntity<?> fallbackMethod(Throwable t) {
	        return new ResponseEntity<>("Service Unavailable",HttpStatus.NOT_FOUND);
	    }
	    
	    
		@GetMapping("/getIdByEmail")
		public ResponseEntity<Map<String, Integer>> getIdByEmail(@RequestParam String email) {
			 return dealer_service.getIdByEmail(email);
		   
		}
		

}
