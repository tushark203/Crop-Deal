package com.payment.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankDetailsDto {
	
	private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String upiId;
    private String upiNumber;
    
    
	
    
    

}
