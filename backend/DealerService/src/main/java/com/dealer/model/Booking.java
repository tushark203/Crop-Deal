package com.dealer.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int bookingId;
	private int dealerId;
	private int cropId;
	private double quantityBooked;
	private double amount;
	private String status ;
	private LocalDateTime Date = LocalDateTime.now();
	
	
//	public int getBookingId() {
//		return bookingId;
//	}
//	public void setBookingId(int bookingId) {
//		this.bookingId = bookingId;
//	}
//	public int getDealerId() {
//		return dealerId;
//	}
//	public void setDealerId(int dealerId) {
//		this.dealerId = dealerId;
//	}
//	public int getCropId() {
//		return cropId;
//	}
//	public void setCropId(int cropId) {
//		this.cropId = cropId;
//	}
//	public double getQuantityBooked() {
//		return quantityBooked;
//	}
//	public void setQuantityBooked(double quantityBooked) {
//		this.quantityBooked = quantityBooked;
//	}
//	
//	
//	public String getStatus() {
//		return status;
//	}
//	public void setStatus(String status) {
//		this.status = status;
//	}
//	public LocalDateTime getDate() {
//		return Date;
//	}
//	public void setDate(LocalDateTime date) {
//		Date = date;
//	}
//	public double getAmount() {
//		return amount;
//	}
//	public void setAmount(double amount) {
//		this.amount = amount;
//	}
//	public Booking(int bookingId, int dealerId, int cropId, double quantityBooked, double amount, String status,
//			LocalDateTime date) {
//		super();
//		this.bookingId = bookingId;
//		this.dealerId = dealerId;
//		this.cropId = cropId;
//		this.quantityBooked = quantityBooked;
//		this.amount = amount;
//		this.status = status;
//		Date = date;
//	}
	
	public Booking(int dealerId, int cropId, double quantityBooked, double amount, String status) {
		
		this.dealerId = dealerId;
		this.cropId = cropId;
		this.quantityBooked = quantityBooked;
		this.amount = amount;
		this.status = status;
	}
	
//	public Booking() {}
	
	
	
	

}
