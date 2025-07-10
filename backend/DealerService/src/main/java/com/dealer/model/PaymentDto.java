package com.dealer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
		private Integer paymetId;
		private Integer dealerId;
	    private Integer farmerId;
	    private Integer cropId;
	    private Double amount;
	    
//	    public Integer getPaymetId() {
//			return paymetId;
//		}
//		public void setPaymetId(Integer paymetId) {
//			this.paymetId = paymetId;
//		}
//		public Integer getDealerId() {
//			return dealerId;
//		}
//		public void setDealerId(Integer dealerId) {
//			this.dealerId = dealerId;
//		}
//		public Integer getFarmerId() {
//			return farmerId;
//		}
//		public void setFarmerId(Integer farmerId) {
//			this.farmerId = farmerId;
//		}
//		public Integer getCropId() {
//			return cropId;
//		}
//		public void setCropId(Integer cropId) {
//			this.cropId = cropId;
//		}
//		public Double getAmount() {
//			return amount;
//		}
//		public void setAmount(Double amount) {
//			this.amount = amount;
//		}
//		
//		
//		public PaymentDto(Integer paymetId, Integer dealerId, Integer farmerId, Integer cropId, Double amount) {
//			super();
//			this.paymetId = paymetId;
//			this.dealerId = dealerId;
//			this.farmerId = farmerId;
//			this.cropId = cropId;
//			this.amount = amount;
//		}
//		public PaymentDto() {
//			
//		}
	    
	    

}
