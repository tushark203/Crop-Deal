package com.notification.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CropDto {
	
	   private String crop_name;
	   private String crop_type;
	   private Double quantity_in_kg;
	   private Double price_per_kg;

}
