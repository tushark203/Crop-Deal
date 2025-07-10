package com.crop.controller;

import java.util.List;
import java.util.Optional;

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

import com.crop.model.Crop;
import com.crop.model.Review;
import com.crop.service.CropService;


import io.swagger.v3.oas.annotations.tags.Tag;


//@CrossOrigin(origins ="http://localhost:4200")
@RestController
@RequestMapping("/crop")
@Tag(name="Crop APIs",description="Crop Deal - Crop Methods")
public class CropController {
	
	@Autowired
	CropService crop_service;
	

	//1. get all published crops -- admin , farmer , dealer
	@GetMapping("/allCrops")
	public ResponseEntity<List<Crop>> getAllCrops()
	{
		return crop_service.getAllCrops();
	}
	
	//2. get details of particular published crop by id -- admin , farmer , dealer
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Crop>> getCropDetailsById(@PathVariable int id)
	{
		return crop_service.getCropDetailsById(id);
	}
	
	//3.publish crop -- farmer
	@PostMapping("/publish")
	public ResponseEntity<String> publishCrop(@RequestBody Crop crop)
	{
		return crop_service.addCrop(crop);
	}
	
	//4.edit published crop -- farmer
	@PutMapping("/edit/{crop_id}")
	public ResponseEntity<String> editCropDetails(@PathVariable int crop_id,@RequestBody Crop crop)
	{
		return crop_service.editCropdetails(crop_id, crop);
	}
	
	//5.delete published crop -- farmer
	@DeleteMapping("/delete/{crop_id}")
	public ResponseEntity<String> deleteCrop(@PathVariable int crop_id)
	{
		return crop_service.deleteCropById(crop_id);
	}


	//6. book crop -- dealer
	@PutMapping("/book/{crop_id}")
	public ResponseEntity<String> bookCrop( @RequestParam("dealer_id") int dealerId,@PathVariable int crop_id,@RequestParam("req_quantity") double req_quantity)
	{
	    return crop_service.bookCropByDealer(dealerId, crop_id,req_quantity);
	}
	
	//8. Cancel booking --dealer
	@PutMapping("/book/cancel/{crop_id}")
	public ResponseEntity<String> cancelCropBooking( @RequestParam("dealer_id") int dealerId, @PathVariable("crop_id") int cropId,@RequestParam("cancel_quantity") double cancel_quantity) {
	    return crop_service.cancelCropBooking(dealerId, cropId,cancel_quantity);
	}
	
	
	//9. list of crops published by particular farmer
	@GetMapping("/your-published-crops")
	public ResponseEntity<List<Crop>> getListOfCropsByFarmerId( @RequestParam("farmer_id") int farmer_id) {
	    return crop_service.getListOfCropsByFarmerId(farmer_id);
	}
	
	
	
	//11 List of available crops
	@GetMapping("/available")
	public ResponseEntity<List<Crop>> availableCrops(){
	    return crop_service.getAvailableCrops();
	}
	
	//12 List of booked crops
	@GetMapping("/booked")
	public ResponseEntity<List<Crop>> BookedCrops(){
	    return crop_service.getBookedCrops();
	}
	
	//13 status of crop
	@GetMapping("/{id}/status")
	public ResponseEntity<String> CropStatus(@PathVariable int id){
	    return crop_service.CropStatus(id);
	}
	
	// get farmer details of that crop -- dealer
	
	@GetMapping("farmer-det/{id}")
	public ResponseEntity<?> getFarmerDetails(@PathVariable int id)
	{
		return crop_service.getFarmer(id);
	}
	
	// get dealer name of that review 
	@GetMapping("dealer-det/{id}")
	public ResponseEntity<?> getDelaerName(@PathVariable int id)
	{
		return crop_service.getDealer(id);
	}
	
	
	
	//--------------review------------------
	
	//1 get all reviews 
	@GetMapping("/reviews")
	public ResponseEntity<List<Review>> allReviews(){
	    return crop_service.allReviews();
	}
	
	//2 get all reviews by crop id 
	@GetMapping("/reviews/{crop_id}")
	public ResponseEntity<List<Review>> allReviewsByCropId(@PathVariable int crop_id ){
	    return crop_service.allReviewsByCropId(crop_id);
	}
	
	//3.post review -- dealer
	@PostMapping("/review/post/{crop_id}")
	public ResponseEntity<String> postReview(@RequestParam("dealer_id") int dealerId, @PathVariable("crop_id") int crop_id ,@RequestBody Review review)
	{
		return crop_service.postReview(dealerId,crop_id,review);
	}
	
//	//4.edit review -- dealer / admin  
//	@PutMapping("/review/edit/{crop_id}")
//	public ResponseEntity<String> editReview(@RequestParam("dealer_id") int dealer_id, @PathVariable("crop_id") int crop_id ,@RequestBody Review review)
//	{
//		return crop_service.editReview(dealer_id,crop_id,review);
//	}
	
	//5.delete review -- admin
	@DeleteMapping("/review/delete/{review_id}")
	public ResponseEntity<String> deleteReview( @PathVariable("review_id") int review_id)
	{
		return crop_service.deleteReview(review_id);
	}
	
	//6.get average rating of crop 
	@GetMapping("/review/average-rating/{crop_id}")
	public ResponseEntity<?> getAverage(@PathVariable("crop_id") int crop_id)
	{
		return crop_service.getAverage(crop_id);
	}

	
	
	
	
	
	
}
