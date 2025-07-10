package com.notification.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("DEALERSERVICE")
public interface NotificationDealerInterface {
	
	 @GetMapping("/dealer/emails")
	 public ResponseEntity<List<String>> getAllEmails();

}
