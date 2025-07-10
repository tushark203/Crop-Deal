package com.farmer.feign;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.farmer.model.CropDto;

@FeignClient("CROPSERVICE")
public interface FarmerInterface {
	
	@PostMapping("/crop/publish")
	public ResponseEntity<String> publishCrop(@RequestBody CropDto cropDto);
	
	@PutMapping("/crop/edit/{crop_id}")
	ResponseEntity<String> editCrop(@PathVariable("crop_id") int cropId, @RequestBody CropDto cropDto);

	@DeleteMapping("/crop/delete/{crop_id}")
	ResponseEntity<String> deleteCrop(@PathVariable("crop_id") int cropId);
	
	
	@GetMapping("crop/allCrops")
	public ResponseEntity<List<CropDto>> getAllCrops();
	

	@GetMapping("crop/your-published-crops")
	public ResponseEntity<List<CropDto>> getListOfCropsByFarmerId( @RequestParam int farmer_id);
	
	@GetMapping("crop/{id}/status")
	public ResponseEntity<String> CropStatus(@PathVariable int id);
	
		
	
}
