package com.farmer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.farmer.feign.FarmerInterface;
import com.farmer.feign.FarmerNotificationInterface;
import com.farmer.model.BankDetails;
import com.farmer.model.CropDto;
import com.farmer.model.Farmer;
import com.farmer.repo.BankDetailsRepo;
import com.farmer.repo.FarmerRepo;

import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class FarmerService {
	
	@Autowired
	FarmerRepo farmerRepo;
	
	@Autowired
	BankDetailsRepo bankRepo;
    
	@Autowired
	FarmerInterface farmerInterface ;
	
	@Autowired
	FarmerNotificationInterface notificationInterface;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public ResponseEntity<List<Farmer>> getAllFarmers() {
		
		try
		{
			return new ResponseEntity<>(farmerRepo.findAll(),HttpStatus.OK) ;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND) ;
		
	}
	

	
    public ResponseEntity<String> deleteFarmerById(int id) {
    	
    	
		
		if (farmerRepo.existsById(id)) {
			farmerRepo.deleteById(id);
	        return new ResponseEntity<>("Farmer deleted succesfully",HttpStatus.OK);
	    } else 
	        return new ResponseEntity<>("Farmer with ID " + id + " does not exist",HttpStatus.NOT_FOUND);	
	}
	
	
	public ResponseEntity<String> registerFarmer(Farmer farmer) {
		Farmer saved = farmerRepo.save(farmer);
		if(saved!=null)
		{
			return new ResponseEntity<>("Farmer registered successfully",HttpStatus.CREATED) ;
		}
		return new ResponseEntity<>("Farmer Registration Unsuccessfull. Try Again",HttpStatus.INTERNAL_SERVER_ERROR) ;
	}
	
	public ResponseEntity<?> getFarmerProfileDetailsById(int id) {
//		if (!isAuthorized(request, id)) {
//	        return new ResponseEntity<>("You are not authorized", HttpStatus.FORBIDDEN);
//	    }
		try
		{
			Optional<Farmer> farmer = farmerRepo.findById(id);
			return new ResponseEntity<>(farmer,HttpStatus.OK) ;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND) ;
		
	}
	
	public ResponseEntity<String> editFarmerProfile(int id ,Farmer farmer,HttpServletRequest request) {
//		if (!isAuthorized(request, id)) {
//	        return new ResponseEntity<>("You are not authorized", HttpStatus.FORBIDDEN);
//	    }
		
		if(farmerRepo.existsById(id))
		{
			Farmer obj = farmerRepo.findById(id).get();
			farmer.setFarmer_id(id);
			farmer.setBankDetails(obj.getBankDetails());
			
			if(farmer.getFirst_name()!=null) obj.setFirst_name(farmer.getFirst_name());
			if(farmer.getLast_name()!=null) obj.setLast_name(farmer.getLast_name());
			if(farmer.getEmail()!=null) obj.setEmail(farmer.getEmail());
			if (farmer.getAddress() != null) obj.setAddress(farmer.getAddress());
	        if (farmer.getDistrict() != null) obj.setDistrict(farmer.getDistrict());
	        if (farmer.getPincode() != null) obj.setPincode(farmer.getPincode());
	        if (farmer.getStatus() != null) obj.setStatus(farmer.getStatus());
	        if (farmer.getPhone_no() != null) obj.setPhone_no(farmer.getPhone_no());
	
			farmerRepo.save(obj);
			return new ResponseEntity<>("Farmer profile updated successfully",HttpStatus.OK) ;
		}
		
		
		return new ResponseEntity<>("Farmer not exist",HttpStatus.NOT_FOUND) ;
		
	}
	
	public ResponseEntity<String> addBankDetails(int farmerId, BankDetails bankDetails) {
	    Optional<Farmer> optionalFarmer = farmerRepo.findById(farmerId);

	    if (optionalFarmer.isPresent()) {
	        Farmer farmer = optionalFarmer.get();
	        
	        BankDetails savedBank = bankRepo.save(bankDetails);

	       
	        farmer.setBankDetails(savedBank);
	        farmerRepo.save(farmer);

	        return new ResponseEntity<>("Bank details added successfully", HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("Farmer not exist", HttpStatus.NOT_FOUND);
	    }
	}
	
	public ResponseEntity<String> updateBankDetails(int farmerId, BankDetails newDetails) {
	    Optional<Farmer> optionalFarmer = farmerRepo.findById(farmerId);

	    if (optionalFarmer.isPresent()) {
	        Farmer farmer = optionalFarmer.get();

	        BankDetails existing = farmer.getBankDetails();

	        if (existing == null) {
	            return new ResponseEntity<>("No existing bank details found.please add bank details", HttpStatus.NOT_FOUND);
	        }

	        // Update fields
	        existing.setBankName(newDetails.getBankName());
	        existing.setAccountNumber(newDetails.getAccountNumber());
	        existing.setIfscCode(newDetails.getIfscCode());
	        existing.setUpiId(newDetails.getUpiId());
	        existing.setUpiNumber(newDetails.getUpiNumber());

	        // Save updated bank info
	        bankRepo.save(existing);

	        return new ResponseEntity<>("Bank details updated successfully", HttpStatus.OK);
	    }

	    return new ResponseEntity<>("Farmer not exist", HttpStatus.NOT_FOUND);
	}

     public ResponseEntity<BankDetails> getBankDetails(int id) {
    	 if(farmerRepo.existsById(id))
 		{
 			Optional<Farmer> farmer = farmerRepo.findById(id);
 			BankDetails details = farmer.get().getBankDetails();
 			
 			return new ResponseEntity<>(details,HttpStatus.OK) ;
 		}
    	 return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	//------------------------------------------crop-service--------------------------------------
	
	public ResponseEntity<String> publishCrop(int farmer_id, CropDto cropDto) {
		
		if(!farmerRepo.existsById(farmer_id))
		{
			 return new ResponseEntity<>("Farmer not exist", HttpStatus.NOT_FOUND);
		}
		
		cropDto.setFarmer_id(farmer_id);
		
		ResponseEntity<String> result = farmerInterface.publishCrop(cropDto);
		
		notificationInterface.publishedCrop(cropDto);
	
		
		return result;
	}

	
	public ResponseEntity<String> editCropByFarmer(int farmerId, int cropId, CropDto cropDto) {
		
		cropDto.setFarmer_id(farmerId);
	      
	    try {
	    	notificationInterface.publishedCrop(cropDto);
	    	return farmerInterface.editCrop(cropId, cropDto);
	    } catch (FeignException ex) {
	      
	        return new ResponseEntity<>("Crop not found", HttpStatus.NOT_FOUND);
	    } 
	}

	
	public ResponseEntity<String> deleteCropByFarmer(int farmerId, int cropId) {
		 try {
			 return farmerInterface.deleteCrop(cropId);
		    } catch (FeignException ex) {
		      
		        return new ResponseEntity<>("Crop not found", HttpStatus.NOT_FOUND);
		    } 
	
	    
	}
	
	

	public ResponseEntity<List<CropDto>> getAllCrops() {
		
			return farmerInterface.getAllCrops();
		
	}

	public ResponseEntity<?> getMyCrops(int farmer_id) {
	        ResponseEntity<List<CropDto>> cropList = farmerInterface.getListOfCropsByFarmerId(farmer_id);
	        return cropList;
		  
		
	}
	public ResponseEntity<String> getCropStatus(int crop_id) {
		return farmerInterface.CropStatus(crop_id);
	}


	//---------------------notification------------------------------------
	public ResponseEntity<String> emailByCropId(int crop_id) {
		List<CropDto> cropList = farmerInterface.getAllCrops().getBody();
		
		int farmerId = 0 ;
		for(CropDto crop:cropList)
		{
			if(crop.getCrop_id()==crop_id)
			{
				farmerId = crop.getFarmer_id();
			}
		}
		
		String email = farmerRepo.findById(farmerId).get().getEmail();
		
		
		return new ResponseEntity<>(email,HttpStatus.OK);
	}


	
//------------authorization-------------------------
	public boolean isAuthorized(HttpServletRequest request, int id) {
		
	    String token = jwtUtil.getToken(request);
	    
	    if (token == null) return false;

	    String role = jwtUtil.extractRole(token);
	    String email = jwtUtil.extractEmail(token);
	    
	    if ("ADMIN".equalsIgnoreCase(role)) {
	    	
	    	
	        return true;
	    }

	    if ("FARMER".equalsIgnoreCase(role)) {
	        Optional<Farmer> farmerOpt = farmerRepo.findByEmail(email);
	        return farmerOpt.isPresent() && farmerOpt.get().getFarmer_id() == id;
	    }

	    return false;
	}



	public ResponseEntity<Map<String, Integer>> getIdByEmail(String email) {
		Optional<Farmer> farmer = farmerRepo.findByEmail(email);
		Map<String, Integer> response = new HashMap<>();
	    if (farmer != null) {
	    	
	    	 response.put("id", farmer.get().getFarmer_id());
	        return ResponseEntity.ok(response);
	    } else {
	    	response.put("id", 0);
	    	return ResponseEntity.ok(response);
	    }
	}
	
	
	

}
