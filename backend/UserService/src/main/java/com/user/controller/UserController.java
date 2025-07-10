package com.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.exception.InvalidCredentialException;
import com.user.model.User;
import com.user.model.UserDto;
import com.user.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/user")
@Tag(name="User APIs",description="Crop Deal - User Methods")
public class UserController {
	
	@Autowired
	UserService user_service;
	
	@GetMapping("/allUsers")
	public ResponseEntity<List<User>> getAllUsers()
	{
		return user_service.getAllUsers();
	}
	
	@PostMapping("/farmer-register")
	public ResponseEntity<String> registerFarmer(@Valid @RequestBody UserDto userDto)
	{
		return user_service.registerFarmer(userDto);
	}
	
	@PostMapping("/dealer-register")
	public ResponseEntity<String> registerDealer(@Valid @RequestBody UserDto userDto)
	{
		return user_service.registerDealer(userDto);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@Valid @RequestBody User user) throws InvalidCredentialException 
	{
		return user_service.login(user);
	}
	
	
	

}
