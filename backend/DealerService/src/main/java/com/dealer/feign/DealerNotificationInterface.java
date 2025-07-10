package com.dealer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dealer.model.NotifyFarmer;




@FeignClient("NOTIFICATIONSERVICE")
public interface DealerNotificationInterface {
	
	@PostMapping("/crop/booked")
	public String bookedCrop(@RequestBody NotifyFarmer notify);

}
