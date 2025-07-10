package com.payment.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
	
	private int dealerId;
	private int cropId;
	private double amount;
	private String status ;
	private double quantityBooked;
	
	
	
	
	

}
