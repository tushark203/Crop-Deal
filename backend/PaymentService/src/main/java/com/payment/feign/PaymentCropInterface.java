package com.payment.feign;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.payment.model.CropDto;


@FeignClient("CROPSERVICE")
public interface PaymentCropInterface {
	
	@GetMapping("/crop/{id}")
	public ResponseEntity<Optional<CropDto>> getCropDetailsById(@PathVariable int id);

}
