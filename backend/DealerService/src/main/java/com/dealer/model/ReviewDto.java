package com.dealer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
	   private String comment;
	   private int rating; //1-5
	   
	   
	   
//	public String getComment() {
//		return comment;
//	}
//	public void setComment(String comment) {
//		this.comment = comment;
//	}
//	public int getRating() {
//		return rating;
//	}
//	public void setRating(int rating) {
//		this.rating = rating;
//	}
//	
//	
//	public ReviewDto(String comment, int rating) {
//		super();
//		this.comment = comment;
//		this.rating = rating;
//	}
//	public ReviewDto() {
//		
//	}
	   
	   

}
