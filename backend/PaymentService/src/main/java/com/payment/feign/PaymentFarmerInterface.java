package com.payment.feign;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.payment.model.FarmerDto;



@FeignClient("FARMERSERVICE")
public interface PaymentFarmerInterface {
	
		@GetMapping("farmer/profile/{id}")
		public ResponseEntity<Optional<FarmerDto>> getFarmerProfileById(@PathVariable int id);

}
