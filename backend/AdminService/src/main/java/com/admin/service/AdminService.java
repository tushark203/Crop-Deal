package com.admin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.admin.feign.AdminCropInterface;
import com.admin.feign.AdminDealerInterface;
import com.admin.feign.AdminFarmerInterface;
import com.admin.model.CropDto;
import com.admin.model.DealerDto;
import com.admin.model.FarmerDto;
import com.admin.model.ReviewDto;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AdminService {
	
	@Autowired
	AdminFarmerInterface farmer_interface;

	@Autowired
	AdminDealerInterface dealer_interface;
	
	@Autowired
	AdminCropInterface crop_interface;
	
	
	//-------------------farmer----------------------------------------
	
	public ResponseEntity<?> getAllFarmers() {
		List<FarmerDto> farmers = farmer_interface.getAllFarmers().getBody();
		if(farmers.isEmpty())
		{
			return new ResponseEntity<>("Farmers not found",HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(farmers ,HttpStatus.OK);
	}

	public ResponseEntity<?> getFarmerProfileDetailsById(int id) {
		Optional<FarmerDto> farmer = farmer_interface.getFarmerProfileById(id).getBody();
		if(farmer.isEmpty())
		{
			return new ResponseEntity<>("Farmer not found",HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(farmer ,HttpStatus.OK);
	}

	public ResponseEntity<String> editFarmerProfile(int id, FarmerDto farmer) {
		return farmer_interface.editProfileOfFarmer(id,farmer);
		
	}

	public ResponseEntity<String> deleteFarmerById(int id) {
		return farmer_interface.deleteFarmer(id);
	}
	
	//-----------------------dealer----------------------------------

	public ResponseEntity<List<DealerDto>> getAllDealer() {
		return dealer_interface.getAllDealers();
	}

	public ResponseEntity<Optional<DealerDto>> getDealerProfileDetailsById(int id) {
		return dealer_interface.getDealerProfileById(id);
	}

	public ResponseEntity<String> editDealerProfile(int id, DealerDto dealer) {
		return dealer_interface.editProfileOfDealer(id, dealer);
	}

	public ResponseEntity<String> deleteDealerById(int id) {
		return dealer_interface.deleteDealer(id);
	}
	
	//-----------------------crop----------------------------------

	public ResponseEntity<List<CropDto>> getAllCrops() {
		return crop_interface.getAllCrops();
	}

	public ResponseEntity<Optional<CropDto>> getCropDetailsById(int id) {
		return crop_interface.getCropDetailsById(id);
	}
	public ResponseEntity<String> deleteCropById(int id) {
		
		return crop_interface.deleteCrop(id);
	}

	public ResponseEntity<List<ReviewDto>> allReviews() {
		return crop_interface.allReviews();
	}

	public ResponseEntity<String> deleteReview(int review_id) {
		return crop_interface.deleteReview(review_id);
	}

	

}
