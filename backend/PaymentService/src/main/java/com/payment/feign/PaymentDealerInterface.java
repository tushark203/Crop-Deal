package com.payment.feign;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.payment.model.BookingDto;
import com.payment.model.DealerDto;


@FeignClient("DEALERSERVICE")
public interface PaymentDealerInterface {
	
	
	@GetMapping("/dealer/profile/{id}")
	public ResponseEntity<Optional<DealerDto>> getDealerProfileById(@PathVariable int id);
	
	@GetMapping("/dealer/bookInfo/{book_id}")
	public ResponseEntity<Optional<BookingDto>> getBookDetails(@PathVariable int book_id);
	
	@GetMapping("/dealer/all-booings")
	public ResponseEntity<List<BookingDto>> allBookings();
	
	
	
	

}
