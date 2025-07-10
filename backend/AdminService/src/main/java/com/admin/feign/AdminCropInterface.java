package com.admin.feign;


import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.admin.model.CropDto;
import com.admin.model.ReviewDto;

@FeignClient("CROPSERVICE")
public interface AdminCropInterface {
	
	@GetMapping("/crop/allCrops")
	public ResponseEntity<List<CropDto>> getAllCrops();
	
	@GetMapping("/crop/{id}")
	public ResponseEntity<Optional<CropDto>> getCropDetailsById(@PathVariable int id);
	
	@DeleteMapping("/delete/{crop_id}")
	public ResponseEntity<String> deleteCrop(@PathVariable int crop_id);

	
	//-------------review----------
	@GetMapping("/crop/reviews")
	public ResponseEntity<List<ReviewDto>> allReviews();
	
	@DeleteMapping("/crop/review/delete/{review_id}")
	public ResponseEntity<String> deleteReview( @PathVariable("review_id") int review_id);
	
	

}
