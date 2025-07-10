package com.dealer.feign;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


import com.dealer.model.CropDto;
import com.dealer.model.ReviewDto;

@FeignClient("CROPSERVICE")
public interface DealerInterface {
   
	
	@GetMapping("crop/allCrops")
	public ResponseEntity<List<CropDto>> getAllCrops();
	
	@GetMapping("crop/{id}")
	public ResponseEntity<Optional<CropDto>> getCropDetailsById(@PathVariable int id);
	
	@PutMapping("crop/book/{crop_id}")
	public ResponseEntity<String> bookCrop( @RequestParam("dealer_id") int dealerId,@PathVariable int crop_id,@RequestParam("req_quantity") double req_quantity);

	@PutMapping("crop/book/cancel/{crop_id}")
	public ResponseEntity<String> cancelCropBooking( @RequestParam("dealer_id") int dealerId, @PathVariable("crop_id") int cropId,@RequestParam("cancel_quantity") double cancel_quantity);

	@GetMapping("crop/your-booked-crops")
	public ResponseEntity<List<CropDto>> getListOfCropsByDealerId( @RequestParam("dealer_id") int dealer_id);
	
	
	//----------review----------
	@PostMapping("crop/review/post/{crop_id}")
	public ResponseEntity<String> postReview(@RequestParam("dealer_id") int dealerId, @PathVariable("crop_id") int crop_id ,@RequestBody ReviewDto review);
	
	@GetMapping("crop/reviews/{crop_id}")
	public ResponseEntity<List<ReviewDto>> allReviewsByCropId(@PathVariable int crop_id );
	
	
}
