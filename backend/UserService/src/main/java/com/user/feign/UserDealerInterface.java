package com.user.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.user.model.UserDto;

@FeignClient("DEALERSERVICE")
public interface UserDealerInterface {
	
	@PostMapping("/dealer/register")
	public ResponseEntity<String> registerDealer(@RequestBody UserDto dealer);

	@GetMapping("/dealer/getIdByEmail")
	public ResponseEntity<Map<String, Integer>> getIdByEmail(@RequestParam String email) ;
}
