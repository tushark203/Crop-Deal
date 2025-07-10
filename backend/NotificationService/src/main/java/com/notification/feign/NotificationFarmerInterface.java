package com.notification.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("FARMERSERVICE")
public interface NotificationFarmerInterface {

	@GetMapping("/farmer/email/{crop_id}")
	public ResponseEntity<String> emailByCropId(@PathVariable int crop_id);
}
