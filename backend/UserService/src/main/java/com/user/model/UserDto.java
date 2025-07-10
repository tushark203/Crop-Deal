package com.user.model;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	   @NotBlank(message = "First name is required")
	   private String first_name ;
	   
	   @NotBlank(message = "First name is required")
	   private String last_name ;
	   
	   @NotBlank(message = "Email is required")
	   @Email(message = "Invalid email format")
	   private String   email ;
	   
	   @NotBlank(message = "Address is required")
	   private String  address;
	   
	   @NotBlank(message = "District is required")
	   private String  district ;
	   
	   @NotBlank(message = "Pincode is required")
	   @Pattern(regexp = "\\d{6}", message = "Pincode must be 6 digits")
	   private String pincode;
	   
	   @NotBlank(message = "Phone number is required")
	   @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
	   private String phone_no ;
	   
	   
	  // @Size(min = 6, message = "Password must be at least 6 characters long")
	   @NotBlank(message = "Password is required")
	   private String  password;


	   

}
