package com.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)	
   private int user_id ;
   
   @NotBlank(message = "Email is mandatory")
   @Email(message = "Email should be valid")
   @Column(unique = true) 
   private String   email ;
   
   @NotBlank(message = "Password is mandatory")
   private String  password;
   
  
   private String role;
   
   

	public User(String email, String password, String role) {
		this.email = email;
		this.password = password;
		this.role = role;
	}

   

}
