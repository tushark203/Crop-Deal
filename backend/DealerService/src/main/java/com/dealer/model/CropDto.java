package com.dealer.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CropDto {
	
   private int crop_id ;
   private int farmer_id;
   private String crop_name;
   private String crop_type;
   private Double quantity_available;
   private Double price_per_kg;

   
//public int getFarmer_id() {
//	return farmer_id;
//}
//public void setFarmer_id(int farmer_id) {
//	this.farmer_id = farmer_id;
//}
//public String getCrop_name() {
//	return crop_name;
//}
//public void setCrop_name(String crop_name) {
//	this.crop_name = crop_name;
//}
//public String getCrop_type() {
//	return crop_type;
//}
//public void setCrop_type(String crop_type) {
//	this.crop_type = crop_type;
//}
//
//public Double getPrice_per_kg() {
//	return price_per_kg;
//}
//public void setPrice_per_kg(Double price_per_kg) {
//	this.price_per_kg = price_per_kg;
//}
//
//   
//public Double getQuantity_available() {
//	return quantity_available;
//}
//public void setQuantity_available(Double quantity_available) {
//	this.quantity_available = quantity_available;
//}
//
//
//
//
//public CropDto(int farmer_id, String crop_name, String crop_type, Double quantity_available, Double price_per_kg) {
//	super();
//	this.farmer_id = farmer_id;
//	this.crop_name = crop_name;
//	this.crop_type = crop_type;
//	this.quantity_available = quantity_available;
//	this.price_per_kg = price_per_kg;
//}
//public CropDto() {
//
//}


}
