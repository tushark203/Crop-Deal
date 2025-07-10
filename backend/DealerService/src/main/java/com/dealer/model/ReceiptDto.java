package com.dealer.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptDto {
	
	  private Integer paymentId;
	  private Double amount;
	  private LocalDateTime paidAt;
	  private String farmerName;
	  private String dealerName;
	  private String cropName;
	  private double quantity;
	  
	  
	  
//	public Integer getPaymentId() {
//		return paymentId;
//	}
//	public void setPaymentId(Integer paymentId) {
//		this.paymentId = paymentId;
//	}
//	public Double getAmount() {
//		return amount;
//	}
//	public void setAmount(Double amount) {
//		this.amount = amount;
//	}
//	public LocalDateTime getPaidAt() {
//		return paidAt;
//	}
//	public void setPaidAt(LocalDateTime paidAt) {
//		this.paidAt = paidAt;
//	}
//	public String getFarmerName() {
//		return farmerName;
//	}
//	public void setFarmerName(String farmerName) {
//		this.farmerName = farmerName;
//	}
//	public String getDealerName() {
//		return dealerName;
//	}
//	public void setDealerName(String dealerName) {
//		this.dealerName = dealerName;
//	}
//	public String getCropName() {
//		return cropName;
//	}
//	public void setCropName(String cropName) {
//		this.cropName = cropName;
//	}
//	public double getQuantity() {
//		return quantity;
//	}
//	public void setQuantity(double quantity) {
//		this.quantity = quantity;
//	}
//	public ReceiptDto(Integer paymentId, Double amount, LocalDateTime paidAt, String farmerName, String dealerName,
//			String cropName, double quantity) {
//		super();
//		this.paymentId = paymentId;
//		this.amount = amount;
//		this.paidAt = paidAt;
//		this.farmerName = farmerName;
//		this.dealerName = dealerName;
//		this.cropName = cropName;
//		this.quantity = quantity;
//	}
//	
//	public ReceiptDto() {
//		
//	}
//	  
	  

}
