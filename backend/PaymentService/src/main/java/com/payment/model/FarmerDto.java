package com.payment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmerDto {
	
	   private int farmer_id ;
	   private String first_name ;
	   private String last_name ;
	   private String   email ;
	   private String  address;
	   private String  district ;
	   private String pincode;
	   private String phone_no ;
	   private BankDetailsDto bankDet;
	   
	   
	   
//	public int getFarmer_id() {
//		return farmer_id;
//	}
//	public void setFarmer_id(int farmer_id) {
//		this.farmer_id = farmer_id;
//	}
//	public String getFirst_name() {
//		return first_name;
//	}
//	public void setFirst_name(String first_name) {
//		this.first_name = first_name;
//	}
//	public String getLast_name() {
//		return last_name;
//	}
//	public void setLast_name(String last_name) {
//		this.last_name = last_name;
//	}
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
//	public String getAddress() {
//		return address;
//	}
//	public void setAddress(String address) {
//		this.address = address;
//	}
//	public String getDistrict() {
//		return district;
//	}
//	public void setDistrict(String district) {
//		this.district = district;
//	}
//	public String getPincode() {
//		return pincode;
//	}
//	public void setPincode(String pincode) {
//		this.pincode = pincode;
//	}
//	public String getPhone_no() {
//		return phone_no;
//	}
//	public void setPhone_no(String phone_no) {
//		this.phone_no = phone_no;
//	}
//	public BankDetailsDto getBankDet() {
//		return bankDet;
//	}
//	public void setBankDet(BankDetailsDto bankDet) {
//		this.bankDet = bankDet;
//	}
//	public FarmerDto(int farmer_id, String first_name, String last_name, String email, String address, String district,
//			String pincode, String phone_no, BankDetailsDto bankDet) {
//		super();
//		this.farmer_id = farmer_id;
//		this.first_name = first_name;
//		this.last_name = last_name;
//		this.email = email;
//		this.address = address;
//		this.district = district;
//		this.pincode = pincode;
//		this.phone_no = phone_no;
//		this.bankDet = bankDet;
//	}
//	
//	public FarmerDto() {
//		
//	}
	   
	   

}
