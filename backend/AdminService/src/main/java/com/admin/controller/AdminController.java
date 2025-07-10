package com.admin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.model.CropDto;
import com.admin.model.DealerDto;
import com.admin.model.FarmerDto;
import com.admin.model.ReviewDto;
import com.admin.service.AdminService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminService admin_service;
	
	//---------------------farmer-------------------------
	//1. all farmers
	@GetMapping("/farmer/allFarmers")
	public ResponseEntity<?> getAllFarmers()
	{
		return admin_service.getAllFarmers();
	}
	
	//2. farmer by id
	@GetMapping("/farmer/profile/{id}")
	public ResponseEntity<?> getFarmerProfileById(@PathVariable int id)
	{
		return admin_service.getFarmerProfileDetailsById(id);
	}
	
	//3 edit profile details //can change status using this (active/inactive)
	@PutMapping("/farmer/profile/edit/{id}")
	public ResponseEntity<String> editProfileOfFarmer(@PathVariable int id,@RequestBody FarmerDto farmer)
	{
		return admin_service.editFarmerProfile(id, farmer);
	}
	
	//4 delete farmer  
	@DeleteMapping("/farmer/delete/{id}")
	public ResponseEntity<String> deleteFarmer(@PathVariable int id)
	{
		return admin_service.deleteFarmerById(id);
	}
	
	//---------------------dealer-------------------------
	
	//1 get all dealers list 
	@GetMapping("/dealer/allDealers")
	public ResponseEntity<List<DealerDto>> getAllDealers()
	{
		return admin_service.getAllDealer();
	} 	
	
	//2 getprofiledetails 
	@GetMapping("/dealer/profile/{id}")
	public ResponseEntity<Optional<DealerDto>> getDealerProfileById(@PathVariable int id)
	{
		return admin_service.getDealerProfileDetailsById(id);
	}

	//3 edit profile details 
	@PutMapping("/dealer/profile/edit/{id}")
	public ResponseEntity<String> editProfileOfDealer(@PathVariable int id,@RequestBody DealerDto dealer)
	{
		return admin_service.editDealerProfile(id, dealer);
	}
	
	//4 delete dealer from list 
	@DeleteMapping("/dealer/delete/{id}")
	public ResponseEntity<String> deleteDealer(@PathVariable int id)
	{
		return admin_service.deleteDealerById(id);
	}
	
	//------------booking----------------------------
	
	
	
	
	//---------------------crop-------------------------
	
	//1. get all published crops 
	@GetMapping("/crop/allCrops")
	public ResponseEntity<List<CropDto>> getAllCrops()
	{
		return admin_service.getAllCrops();
	}
	
	//2. get details of particular published crop by id 
	@GetMapping("/crop/{id}")
	public ResponseEntity<Optional<CropDto>> getCropDetailsById(@PathVariable int id)
	{
		return  admin_service.getCropDetailsById(id);
	}
	
	@DeleteMapping("/crop/delete/{id}")
	public ResponseEntity<String> deleteCrop(@PathVariable int id)
	{
		return admin_service.deleteCropById(id);
	}
	
	
	
	//---------------------review-------------------------
	//1 get all reviews 
	@GetMapping("/crop/reviews")
	public ResponseEntity<List<ReviewDto>> allReviews(){
	    return admin_service.allReviews();
	}
	
	//2 delete review
	@DeleteMapping("/crop/review/delete/{review_id}")
	public ResponseEntity<String> deleteReview( @PathVariable("review_id") int review_id)
	{
		return admin_service.deleteReview(review_id);
	}
}
