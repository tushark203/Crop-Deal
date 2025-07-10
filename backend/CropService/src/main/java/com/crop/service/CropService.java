package com.crop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.crop.feign.CropDealer;
import com.crop.feign.CropInterface;
import com.crop.model.Crop;
import com.crop.model.Review;
import com.crop.repo.CropRepo;
import com.crop.repo.ReviewRepo;



@Service
public class CropService {
	
	@Autowired
	CropRepo cropRepo;
	
	@Autowired
	ReviewRepo reviewRepo;
	
	@Autowired
	CropInterface crop_interface;
	
	@Autowired
	CropDealer crop_dealer;
	
	

	public ResponseEntity<List<Crop>> getAllCrops() {
		try
		{
			return new ResponseEntity<>(cropRepo.findAll(),HttpStatus.OK) ;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND) ;
		
	}


	public ResponseEntity<Optional<Crop>> getCropDetailsById(int id) {
		try
		{
			Optional<Crop> crop = cropRepo.findById(id);
			return new ResponseEntity<>(crop,HttpStatus.OK) ;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND) ;
	}


//	public ResponseEntity<String> addCrop(int farmer_id,Crop crop) {
//		crop.setFarmer_id(farmer_id);
//        cropRepo.save(crop);
//		
//		return new ResponseEntity<>("crop registered successfully",HttpStatus.CREATED) ;
//		
//	}
	
	public ResponseEntity<String> addCrop(Crop crop) {
		//initial available quantity is equals to publish quantity
		crop.setQuantity_available(crop.getQuantity_in_kg());
        Crop crop2 = cropRepo.save(crop);
        if(crop2!=null)
        {
        	return new ResponseEntity<>("Crop registered successfully",HttpStatus.CREATED);
        }
		
        return new ResponseEntity<>("Crop regisration unsuccessfull",HttpStatus.INTERNAL_SERVER_ERROR);
		
	}


	public ResponseEntity<String> editCropdetails(int id, Crop crop) {
		if(cropRepo.existsById(id))
		{
			crop.setQuantity_available(crop.getQuantity_in_kg());
			crop.setCrop_id(id);
			cropRepo.save(crop);
			return new ResponseEntity<>("Crop details updated successfully",HttpStatus.OK) ;
		}
		
		
		return new ResponseEntity<>("Crop not found",HttpStatus.NOT_FOUND) ;
	}


	public ResponseEntity<String> deleteCropById(int crop_id) {
		
		if (cropRepo.existsById(crop_id)) {
	        cropRepo.deleteById(crop_id);
	        return ResponseEntity.ok("Crop deleted successfully");
	    } else 
	        return new ResponseEntity<>("Crop not exist",HttpStatus.NOT_FOUND);	
	}
	



	public ResponseEntity<String> bookCropByDealer(int dealerId, int cropId , double req_quantity ) {
	    Optional<Crop> optionalCrop = cropRepo.findById(cropId);

	    if (optionalCrop.isEmpty()) {
	        return new ResponseEntity<>("Crop not found", HttpStatus.NOT_FOUND);
	    }

	    Crop crop = optionalCrop.get();
	    if ("Booked".equalsIgnoreCase(crop.getStatus())) {
	        return new ResponseEntity<>("Crop is already sold. Crop is not available", HttpStatus.BAD_REQUEST);
	    }
	    
	    
	    if(crop.getQuantity_available()==null)
	    {
	    	 return new ResponseEntity<>("No quantity available", HttpStatus.NOT_FOUND);
	    }
	    
	    if (crop.getQuantity_available() < req_quantity) {
        return new ResponseEntity<>("Insufficient quantity", HttpStatus.NOT_FOUND);
         }
	    

	  
	    
	    crop.setQuantity_available(crop.getQuantity_available()-req_quantity);
	    crop.setQuantity_booked(crop.getQuantity_booked() == null?req_quantity:crop.getQuantity_booked()+req_quantity);
	    
	    
	    if (crop.getQuantity_available() == 0) {
                crop.setStatus("Booked");
           }
	   
	    cropRepo.save(crop);
	    

	
	    return new ResponseEntity<>("Crop booked successfully", HttpStatus.OK);
	}

	public ResponseEntity<String> cancelCropBooking(int dealerId, int cropId,double cancel_quantity) {
	    Optional<Crop> optionalCrop = cropRepo.findById(cropId);
	    if (optionalCrop.isEmpty()) {
	        return new ResponseEntity<>("Crop not found", HttpStatus.NOT_FOUND);
	    }
	    
	    Crop crop = optionalCrop.get();

	    crop.setQuantity_available(crop.getQuantity_available() + cancel_quantity);
	    crop.setQuantity_booked(crop.getQuantity_booked() - cancel_quantity);
	    
	    crop.setStatus("Available");
	    cropRepo.save(crop);
	    
    
	    return new ResponseEntity<>("Crop unbooked successfully"+cancel_quantity+" quantity is cancelled", HttpStatus.OK);
	}


	public ResponseEntity<List<Crop>> getListOfCropsByFarmerId(int farmer_id) {
		List<Crop> cropList = cropRepo.findCropsByFarmerId(farmer_id);
		
		if(!cropList.isEmpty())
		{
			return new ResponseEntity<>(cropList,HttpStatus.OK);
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND);
	}





	public ResponseEntity<List<Crop>> getAvailableCrops() {
        List<Crop> cropList = cropRepo.findCropByQuantityAvailableGreaterThan(0.0);
		
		if(!cropList.isEmpty())
		{
			return new ResponseEntity<>(cropList,HttpStatus.OK);
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND);
	}


	public ResponseEntity<List<Crop>> getBookedCrops() {
       List<Crop> cropList = cropRepo.findCropByQuantityAvailableIsZero(0.0);
		
		if(!cropList.isEmpty())
		{
			return new ResponseEntity<>(cropList,HttpStatus.OK);
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND);
	}


	public ResponseEntity<String> CropStatus(int id) {
		Optional<Crop> oCrop = cropRepo.findById(id);
		 if (oCrop.isEmpty())
		 {
		        return new ResponseEntity<>("Crop not found", HttpStatus.NOT_FOUND);
		 }
		 
		 return new ResponseEntity<>("Crop Status : "+oCrop.get().getStatus(), HttpStatus.OK);
	}


	
	//-------------------review methods------------------------------------
	public ResponseEntity<List<Review>> allReviews() {
		try
		{
			return new ResponseEntity<>(reviewRepo.findAll(),HttpStatus.OK) ;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND) ;
	
	}


	public ResponseEntity<List<Review>> allReviewsByCropId(int crop_id) {
		try
		{
			return new ResponseEntity<>(reviewRepo.findAllByCropId(crop_id),HttpStatus.OK) ;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND) ;
	}


	public ResponseEntity<String> postReview(int dealerId, int crop_id, Review review) {
		review.setCropId(crop_id);
		review.setDealerId(dealerId);
		 if(review.getRating()>5)
		 {
			 review.setRating(5);
		 }
		 
		 if(review.getRating()<1)
		 {
			 review.setRating(1);
		 }
        Review saved = reviewRepo.save(review);
        if(saved!=null)
        {
        	return new ResponseEntity<>("review posted successfully",HttpStatus.CREATED);
        }
		
        return new ResponseEntity<>("Not able to post review",HttpStatus.INTERNAL_SERVER_ERROR);
	
	}


	public ResponseEntity<String> editReview(int dealer_id, int crop_id, Review review) {
		
		List<Review> reviewList = reviewRepo.findByDealerId(dealer_id);
		Review toBeEdited = null ;
		
		for(Review r : reviewList)
		{
			if(r.getCropId()==crop_id)
			{
				toBeEdited = r;
			}
		}
		
		if(toBeEdited!=null)
		{
			review.setReviewId(toBeEdited.getReviewId());
			review.setCropId(crop_id);
			review.setDealerId(dealer_id);
			
			reviewRepo.save(review);
			
			return new ResponseEntity<>("review edited successfully",HttpStatus.CREATED);
		}
		 return new ResponseEntity<>("Review not found",HttpStatus.NOT_FOUND);
	}


	public ResponseEntity<String> deleteReview(int review_id) {
		
		if (reviewRepo.existsById(review_id)) {
			reviewRepo.deleteById(review_id);
	        return ResponseEntity.ok("review deleted successfully");
	    } else 
	        return new ResponseEntity<>("Review does not exist",HttpStatus.NOT_FOUND);	
	}


	public ResponseEntity<?> getAverage(int crop_id) {
		List<Review> reviewList = reviewRepo.findAllByCropId(crop_id);
		double total = 0;
		double average = 0;
		
		
		if(!reviewList.isEmpty())
		{
			for(Review r : reviewList)
			{
				total += r.getRating();
			}
			
			average = total/reviewList.size();
			
			return new ResponseEntity<>(average,HttpStatus.OK);
		}
		 return new ResponseEntity<>("Crop not found",HttpStatus.NOT_FOUND);
	}


	public ResponseEntity<?> getFarmer(int id) {
		return crop_interface.getFarmerProfileById(id);
	}
	
	public ResponseEntity<?> getDealer(int id) {
		return crop_dealer.getDealerProfileById(id);
	}




	





}
