package com.crop.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient("FARMERSERVICE" )
public interface CropInterface {
	
	@GetMapping("/farmer/profile/{id}")
	public ResponseEntity<?> getFarmerProfileById(@PathVariable int id);
	
	

}
