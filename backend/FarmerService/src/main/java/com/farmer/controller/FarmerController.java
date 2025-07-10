package com.farmer.controller;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
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

import com.farmer.model.BankDetails;
import com.farmer.model.CropDto;
import com.farmer.model.Farmer;
import com.farmer.service.FarmerService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/farmer")
@Tag(name="Farmer APIs",description="Crop Deal - Farmer Methods")
public class FarmerController {
	
	@Autowired
	FarmerService farmer_service;
	
	
	//1 get all farmers list -- admin
	@GetMapping("/allFarmers")
	public ResponseEntity<List<Farmer>> getAllFarmers()
	{
		return farmer_service.getAllFarmers();
	}
	
	//2 delete farmerfrom list -- admin
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteFarmer(@PathVariable int id)
	{
		return farmer_service.deleteFarmerById(id);
	}
	
	//3 register/add farmer -- admin,farmer
	@PostMapping("/register")
	public ResponseEntity<String> registerFarmer(@RequestBody Farmer farmer)
	{
		return farmer_service.registerFarmer(farmer);
	}
	
	//4 getprofiledetails -- admin,farmer
	@GetMapping("/profile/{id}")
	public ResponseEntity<?> getFarmerProfileById(@PathVariable int id)
	{
		return farmer_service.getFarmerProfileDetailsById(id);
	}
	
	//5 edit profile details -- admin ,farmer
	@PutMapping("/profile/edit/{id}")
	public ResponseEntity<String> editProfileOfFarmer(@PathVariable int id,@RequestBody Farmer farmer,HttpServletRequest request)
	{
	
		return farmer_service.editFarmerProfile(id, farmer,request);
	}
	
	//6 add payment details --farmer
	@PostMapping("/{id}/add-bank-details")
	public ResponseEntity<String> addBankDetails(@PathVariable int id, @RequestBody BankDetails bankDetails) {
	    return farmer_service.addBankDetails(id, bankDetails);
	}
	
	//7 edit/update payment details -- farmer
	@PutMapping("/{id}/update-bank-details")
	public ResponseEntity<String> updateBankDetails(@PathVariable int id, @RequestBody BankDetails newDetails) {
	    return farmer_service.updateBankDetails(id, newDetails);
	}
	
	//8 get bank details by id
	@GetMapping("/bankDetails/{id}")
	public ResponseEntity<BankDetails> getBankDetails(@PathVariable int id)
	{
		return farmer_service.getBankDetails(id);
	}
	
	
	
	//interaction with crop-service
	//1 publish crop -- farmer 
	@PostMapping("/{farmer_id}/publish-crop")
	public ResponseEntity<String> publishCrop(@PathVariable int farmer_id, @RequestBody CropDto cropDto) {
	    return farmer_service.publishCrop(farmer_id, cropDto);
	}
	
	//2 edit published crop -- farmer
	@PutMapping("/{farmer_id}/edit-crop/{crop_id}")
	public ResponseEntity<String> editCrop( @PathVariable int farmer_id, @PathVariable int crop_id, @RequestBody CropDto cropDto) {
	    return farmer_service.editCropByFarmer(farmer_id, crop_id, cropDto);
	}

	//3 deletepublish crop details-- farmer
	@DeleteMapping("/{farmer_id}/delete-crop/{crop_id}")
	public ResponseEntity<String> deleteCrop( @PathVariable int farmer_id,@PathVariable int crop_id) {
	    return farmer_service.deleteCropByFarmer(farmer_id, crop_id);
	}
	
	//4 get all crops
	@GetMapping("/allCrops")
	public ResponseEntity<List<CropDto>> getAllCrops()
	{
		return farmer_service.getAllCrops();
	}
	
	//5 get crops published by me(farmer)
	@GetMapping("{farmer_id}/MyCrops")
	public ResponseEntity<?> getMyCrops(@PathVariable int farmer_id)
	{
		return farmer_service.getMyCrops(farmer_id);
	}
	
	//6 crop status
	@GetMapping("/{crop_id}/status")
	public ResponseEntity<String> CropStatus(@PathVariable int crop_id)
	{
		return farmer_service.getCropStatus(crop_id);
	}
	
	//----------------------------for notification---------------------------
	
	@GetMapping("/email/{crop_id}")
	public ResponseEntity<String> emailByCropId(@PathVariable int crop_id)
	{
		return farmer_service.emailByCropId(crop_id);
	}
	
	//---------get farmer id for angular-----------------
	@GetMapping("/getIdByEmail")
	public ResponseEntity<Map<String, Integer>> getIdByEmail(@RequestParam String email) {
		 return farmer_service.getIdByEmail(email);
	   
	}
	
}
