package com.dealer.model;





import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dealer {

	 @Id
	   @GeneratedValue(strategy=GenerationType.IDENTITY)	
	   private int dealer_id ;
	   private String first_name ;
	   private String last_name ;
	   private String   email ;
	   private String  address;
	   private String  district ;
	   private String pincode;
	   private String status = "Active" ;
	   private String phone_no ;
	   
	   @OneToOne(cascade = CascadeType.ALL)
	   @JoinColumn(name = "bank_details_id", referencedColumnName = "bank_details_id")
	   private BankDetails bankDetails;

//	public int getDealer_id() {
//		return dealer_id;
//	}
//
//	public void setDealer_id(int dealer_id) {
//		this.dealer_id = dealer_id;
//	}
//
//	public String getFirst_name() {
//		return first_name;
//	}
//
//	public void setFirst_name(String first_name) {
//		this.first_name = first_name;
//	}
//
//	public String getLast_name() {
//		return last_name;
//	}
//
//	public void setLast_name(String last_name) {
//		this.last_name = last_name;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public String getAddress() {
//		return address;
//	}
//
//	public void setAddress(String address) {
//		this.address = address;
//	}
//
//	public String getDistrict() {
//		return district;
//	}
//
//	public void setDistrict(String district) {
//		this.district = district;
//	}
//
//	public String getPincode() {
//		return pincode;
//	}
//
//	public void setPincode(String pincode) {
//		this.pincode = pincode;
//	}
//
//	public String getStatus() {
//		return status;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}
//
//	public String getPhone_no() {
//		return phone_no;
//	}
//
//	public void setPhone_no(String phone_no) {
//		this.phone_no = phone_no;
//	}
//
//	public BankDetails getBankDetails() {
//		return bankDetails;
//	}
//
//	public void setBankDetails(BankDetails bankDetails) {
//		this.bankDetails = bankDetails;
//	}
//	   
	   
}
