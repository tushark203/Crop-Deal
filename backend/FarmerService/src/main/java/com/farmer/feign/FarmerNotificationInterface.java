package com.farmer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.farmer.model.CropDto;



@FeignClient("NOTIFICATIONSERVICE")
public interface FarmerNotificationInterface {
	
	@PostMapping("/crop/published/now")
    public String publishedCrop(@RequestBody CropDto crop);

}
