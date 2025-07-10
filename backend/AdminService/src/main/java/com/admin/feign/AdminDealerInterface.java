package com.admin.feign;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.admin.model.DealerDto;

@FeignClient("DEALERSERVICE")
public interface AdminDealerInterface {

	@GetMapping("/dealer/allDealers")
	public ResponseEntity<List<DealerDto>> getAllDealers();
	
	@GetMapping("/dealer/profile/{id}")
	public ResponseEntity<Optional<DealerDto>> getDealerProfileById(@PathVariable int id);
	
	@PutMapping("/dealer/profile/edit/{id}")
	public ResponseEntity<String> editProfileOfDealer(@PathVariable int id,@RequestBody DealerDto dealer);
	
	@DeleteMapping("/dealer/delete/{id}")
	public ResponseEntity<String> deleteDealer(@PathVariable int id);
	
	
}
