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

import com.admin.model.FarmerDto;

import jakarta.servlet.http.HttpServletRequest;

@FeignClient("FARMERSERVICE")
public interface AdminFarmerInterface {

	@GetMapping("/farmer/allFarmers")
	public ResponseEntity<List<FarmerDto>> getAllFarmers();
	
	@GetMapping("/farmer/profile/{id}")
	public ResponseEntity<Optional<FarmerDto>> getFarmerProfileById(@PathVariable int id);
	
	@PutMapping("/farmer/profile/edit/{id}")
	public ResponseEntity<String> editProfileOfFarmer(@PathVariable int id,@RequestBody FarmerDto farmer);
	
	@DeleteMapping("/farmer/delete/{id}")
	public ResponseEntity<String> deleteFarmer(@PathVariable int id);
}
