package com.notification.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotifyFarmer {
	
	private int crop_id ;
	private String dealerName;
	private String cropType;
	private String cropName;
	private double bookedQuantity;
	

}
