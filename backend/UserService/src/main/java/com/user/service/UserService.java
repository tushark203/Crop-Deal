package com.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.exception.InvalidCredentialException;
import com.user.feign.UserDealerInterface;
import com.user.feign.UserFarmerInterface;
import com.user.model.User;
import com.user.model.UserDto;
import com.user.repo.UserRepo;

@Service
public class UserService {
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	UserFarmerInterface farmer_service;
	
	@Autowired
	UserDealerInterface dealer_service;
	@Autowired
    private JwtService jwtService;
	
	@Autowired
	AuthenticationManager authManager;

	public ResponseEntity<String> registerFarmer(UserDto userDto) {
		
		 if (userRepo.existsByEmail(userDto.getEmail())) {
		        return ResponseEntity.status(HttpStatus.CONFLICT)
		                .body("User is already exist with this email.");
		    }
		
		User user = new User(userDto.getEmail(),encoder.encode(userDto.getPassword()),"FARMER");
		
		User saved = userRepo.save(user);
		if(saved==null)
		{
			return new ResponseEntity<>("user registration failed",HttpStatus.INTERNAL_SERVER_ERROR) ;
		}
		
		return farmer_service.registerFarmer(userDto);
	
	}
	
	public ResponseEntity<String> registerDealer(UserDto userDto) {
		 if (userRepo.existsByEmail(userDto.getEmail())) {
		        return ResponseEntity.status(HttpStatus.CONFLICT)
		                .body("User is already exist with this email.");
		    }
		User user = new User(userDto.getEmail(),encoder.encode(userDto.getPassword()),"DEALER");
		
		User saved = userRepo.save(user);
		if(saved==null)
		{
			return new ResponseEntity<>("user registration failed",HttpStatus.INTERNAL_SERVER_ERROR) ;
		}
		
		return dealer_service.registerDealer(userDto);
	
	}
	
	
	public ResponseEntity<Map<String, String>> login(User loginUser) throws InvalidCredentialException  {
		String responseForId = null;

		 try {
		        Authentication authentication = authManager.authenticate(
		            new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword())
		        );
		        
		        

		        if (authentication.isAuthenticated()) {
		            User user = userRepo.findByEmail(loginUser.getEmail());
		            
		            if(user.getRole().equalsIgnoreCase("admin"))
		            {
		            	String token = jwtService.generateToken(user,"0");
		            	  Map<String, String> response = new HashMap<>();
				            response.put("token", token);
				            
				     
				            return ResponseEntity.ok(response);
		            }
		            
		            if(user.getRole().equalsIgnoreCase("farmer"))
		             {
		            	 responseForId = farmer_service.getIdByEmail(loginUser.getEmail()).getBody().get("id").toString();
		             }
		             else  if(user.getRole().equalsIgnoreCase("dealer"))
		             {
		            	 responseForId = dealer_service.getIdByEmail(loginUser.getEmail()).getBody().get("id").toString();
		             }
		            
		            String token = jwtService.generateToken(user,responseForId);
		            
		            
		            
		            Map<String, String> response = new HashMap<>();
		            response.put("token", token);
		            
		     
		            return ResponseEntity.ok(response);
		        } else {
		            throw new InvalidCredentialException("Invalid credentials");
		        }

		    } catch (BadCredentialsException ex) {
		        throw new InvalidCredentialException("Invalid credentials");
		    }
	}
	 	 

	public ResponseEntity<List<User>> getAllUsers() {
		try
		{
			return new ResponseEntity<>(userRepo.findAll(),HttpStatus.OK) ;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND) ;
	}
	
	

	
	

	

}
