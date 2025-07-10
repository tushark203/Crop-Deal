package com.dealer.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dealer.feign.DealerInterface;
import com.dealer.feign.DealerNotificationInterface;
import com.dealer.feign.DealerPaymentInterface;
import com.dealer.model.BankDetails;
import com.dealer.model.Booking;
import com.dealer.model.CropDto;
import com.dealer.model.Dealer;
import com.dealer.model.NotifyFarmer;
import com.dealer.model.PaymentDto;
import com.dealer.model.ReceiptDto;
import com.dealer.model.ReviewDto;
import com.dealer.repo.BankDetailsRepo;
import com.dealer.repo.BookingRepo;
import com.dealer.repo.DealerRepo;


import feign.FeignException;

@Service
public class DealerService {
	
	@Autowired
	DealerRepo dealerRepo;
	
	@Autowired
	BankDetailsRepo bankRepo;
	
	@Autowired
	BookingRepo bookingRepo;
	
	@Autowired
	DealerInterface dealerInterface;
	
	@Autowired
	DealerPaymentInterface paymentInterface;
	
	@Autowired
	DealerNotificationInterface notificationInterface;
	
	public ResponseEntity<List<Dealer>> getAllDealer() {
		try
		{
			return new ResponseEntity<>(dealerRepo.findAll(),HttpStatus.OK) ;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND) ;
		
	}
	
  public ResponseEntity<String> deleteDealerById(int id) {
		
		if (dealerRepo.existsById(id)) {
			dealerRepo.deleteById(id);
	        return ResponseEntity.ok("Dealer deleted succesfully");
	    } else 
	        return new ResponseEntity<>("Dealer not exist",HttpStatus.NOT_FOUND);	
	}
	
	public ResponseEntity<String> registerDealer(Dealer dealer) {
		Dealer saved_dealer=dealerRepo.save(dealer);
		if(saved_dealer!=null)
		{
			return new ResponseEntity<>("Dealer registered successfully",HttpStatus.CREATED) ;
		}
		
		return new ResponseEntity<>("Dealer registration unsuccessfull",HttpStatus.INTERNAL_SERVER_ERROR) ;
		
	}
	
	public ResponseEntity<Optional<Dealer>> getDealerProfileDetailsById(int id) {
		try
		{
			Optional<Dealer> dealer = dealerRepo.findById(id);
			return new ResponseEntity<>(dealer,HttpStatus.OK) ;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND) ;
		
	}
	
	public ResponseEntity<String> editDealerProfile(int id ,Dealer dealer) {
		if(dealerRepo.existsById(id))
		{
			dealer.setDealer_id(id);
			dealerRepo.save(dealer);
			return new ResponseEntity<>("Dealer profile updated successfully",HttpStatus.OK) ;
		}
		
		
		return new ResponseEntity<>("Dealer not exit",HttpStatus.NOT_FOUND) ;
		
	}
	
	public ResponseEntity<String> addBankDetails(int dealerId, BankDetails bankDetails) {
	    Optional<Dealer> optionalDealer = dealerRepo.findById(dealerId);

	    if (optionalDealer.isPresent()) {
	    	Dealer dealer = optionalDealer.get();
	        
	        BankDetails savedBank = bankRepo.save(bankDetails);

	       
	        dealer.setBankDetails(savedBank);
	        dealerRepo.save(dealer);

	        return new ResponseEntity<>("Bank details added successfully", HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("Dealer not exist", HttpStatus.NOT_FOUND);
	    }
	}
	
	public ResponseEntity<String> updateBankDetails(int dealerId, BankDetails newDetails) {
	    Optional<Dealer> optionalDealer = dealerRepo.findById(dealerId);

	    if (optionalDealer.isPresent()) {
	    	Dealer dealer = optionalDealer.get();

	        BankDetails existing = dealer.getBankDetails();

	        if (existing == null) {
	            return new ResponseEntity<>("No existing bank details found.please add bank details", HttpStatus.NOT_FOUND);
	        }

	        // Update fields
	        existing.setBankName(newDetails.getBankName());
	        existing.setAccountNumber(newDetails.getAccountNumber());
	        existing.setIfscCode(newDetails.getIfscCode());
	        existing.setUpiId(newDetails.getUpiId());
	        existing.setUpiNumber(newDetails.getUpiNumber());

	        // Save updated bank info
	        bankRepo.save(existing);

	        return new ResponseEntity<>("Bank details updated successfully", HttpStatus.OK);
	    }

	    return new ResponseEntity<>("Dealer not exist", HttpStatus.NOT_FOUND);
	}
	
	
	 public ResponseEntity<BankDetails> getBankDetails(int id) {
    	 if(dealerRepo.existsById(id))
 		{
 			Optional<Dealer> dealer =dealerRepo.findById(id);
 			BankDetails details = dealer.get().getBankDetails();
 			
 			return new ResponseEntity<>(details,HttpStatus.OK) ;
 		}
    	 return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	
	// crop service

//	public ResponseEntity<List<CropDto>> getAllPubishedCrops() {
//		return dealerInterface.getAllCrops();
//	     
//	}
	 
	 public ResponseEntity<?> getAllPubishedCrops() {
		 List<CropDto> cropList = dealerInterface.getAllCrops().getBody();
			if(cropList.isEmpty())
			{
				return new ResponseEntity<>("No crops found",HttpStatus.NOT_FOUND);
				
			}
			return new ResponseEntity<>(cropList,HttpStatus.OK);
		     
		}

	public ResponseEntity<String> bookPubishedCrop(int dealer_id,int crop_id,double req_quantity) {
		
		Dealer dealer = dealerRepo.findById(dealer_id).get();
		CropDto cropDet = dealerInterface.getCropDetailsById(crop_id).getBody().get();
		if(req_quantity<=0)
		{
			return new ResponseEntity<>("value must be greater than 0 ",HttpStatus.BAD_REQUEST);
		}
		
		List<Booking> bookingList = bookingRepo.findAll();
		Booking ifExist = null;
		for(Booking b : bookingList )
		{
			if(b.getDealerId()==dealer_id && b.getCropId() == crop_id && b.getStatus().equals("book"))
			{
				ifExist = b;
			}
		}
		double cropPrice = cropDet.getPrice_per_kg();
		double amount = cropPrice*req_quantity;
		
		if(ifExist!=null)
		{
			ifExist.setBookingId(ifExist.getBookingId());
			ifExist.setAmount(ifExist.getAmount()+amount);
			ifExist.setQuantityBooked(ifExist.getQuantityBooked()+req_quantity);
			
		}
		
		NotifyFarmer notify  = new NotifyFarmer();
		notify.setDealerName(dealer.getFirst_name()+" "+dealer.getLast_name());
		notify.setCrop_id(crop_id);
		notify.setCropName(cropDet.getCrop_name());
		notify.setCropType(cropDet.getCrop_type());
		notify.setBookedQuantity(req_quantity);
		
	
		
		
		ResponseEntity<String> res = dealerInterface.bookCrop(dealer_id,crop_id,req_quantity);
	
		if(res.getStatusCode() != HttpStatus.OK)
		{
			
			return new ResponseEntity<>(res.getBody(),HttpStatus.BAD_REQUEST);
		}
		else
		{
			if(ifExist!=null)
			{
				bookingRepo.save(ifExist);
			}
			else
			{
				bookingRepo.save(new Booking(dealer_id,crop_id,req_quantity,amount,"book"));
			}
			
			notificationInterface.bookedCrop(notify);
			return res;
		}
		
		
	}

	public ResponseEntity<String> cancelBooking(int dealer_id,int crop_id,double cancel_quantity) {
		if(cancel_quantity<=0)
		{
			return new ResponseEntity<>("value must be greater than 0 ",HttpStatus.BAD_REQUEST);
		}
		
		List<Booking> bookingList = bookingRepo.findAll();
		Booking toUpdated = null;
		for(Booking b : bookingList )
		{
			if(b.getDealerId()==dealer_id && b.getCropId() == crop_id && b.getStatus().equals("book"))
			{
				toUpdated = b;
			}
		}
		
		if(toUpdated==null)
		{
			return new ResponseEntity<>("Booking Not Found",HttpStatus.NOT_FOUND);
		}
		
		CropDto cropDet = dealerInterface.getCropDetailsById(crop_id).getBody().get();
		
		
		double cropPrice = cropDet.getPrice_per_kg();
		double amount = cropPrice*cancel_quantity;
		if(toUpdated.getQuantityBooked()<cancel_quantity)
		{
			return new ResponseEntity<>("cancel quantity must be greater than booked quantity",HttpStatus.BAD_REQUEST);
		}
		 
		toUpdated.setBookingId(toUpdated.getBookingId());
		toUpdated.setDate(LocalDateTime.now());
		toUpdated.setAmount(toUpdated.getAmount()-amount);
		toUpdated.setQuantityBooked(toUpdated.getQuantityBooked()-cancel_quantity);
		
		bookingRepo.save(toUpdated);
		
		bookingRepo.save(new Booking(dealer_id,crop_id,cancel_quantity,amount,"cancel"));
		return dealerInterface.cancelCropBooking(dealer_id, crop_id,cancel_quantity);
	}
	
		public ResponseEntity<Optional<Booking>> getBookDetails(int book_id) {
			
			Optional<Booking> booking = bookingRepo.findById(book_id);
			
			if(booking.get()==null)
			{
				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(booking,HttpStatus.OK);
		}

	
	public ResponseEntity<?> getMyBookedCrops(int dealer_id) {
		return new ResponseEntity<>( bookingRepo.findAllByDealerIdAndStatus(dealer_id,"book"),HttpStatus.OK);
	}

	public ResponseEntity<String> postReview(int dealer_id, int crop_id, ReviewDto review) {
		return dealerInterface.postReview(dealer_id,crop_id,review);
		
	}
	public ResponseEntity<List<Booking>> allBookings() {
		List<Booking> bookingList = bookingRepo.findAll();
		if(bookingList.isEmpty())
		{
			return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>( bookingList,HttpStatus.OK);
	}

	public ResponseEntity<Optional<CropDto>> getCropDetailsById(int id) {
		
		return dealerInterface.getCropDetailsById(id);
	}

	public ResponseEntity<String> getAmountToBePaid(int book_id) {
		
		Optional<Booking> book = bookingRepo.findById(book_id);
		if(book.isEmpty())
		{
			return new ResponseEntity<>("You don't have bookings or entered wrong id", HttpStatus.NOT_FOUND);
		}
		
		try {
			return paymentInterface.getAmountToBePaid(book_id);
		}
		catch(FeignException ex)
		{
		
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		
	}

	public ResponseEntity<String> makePayment(PaymentDto payment) {
		if (payment.getAmount() <= 0) {
            return new ResponseEntity<>("amount should be positive", HttpStatus.BAD_REQUEST);
        }

        try {
            return paymentInterface.makePayment(payment);
        } catch (FeignException.InternalServerError ex) {
           
            return new ResponseEntity<>("Amount is not matched or any internal error.", HttpStatus.SERVICE_UNAVAILABLE);
        } catch (FeignException ex) {
           
            return new ResponseEntity<>("Failed to make payment: " + ex.getMessage(), HttpStatus.BAD_GATEWAY);
        } catch (Exception ex) {
            
            return new ResponseEntity<>("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}

	public ResponseEntity<List<PaymentDto>> forDealerPayments(int dealer_id) {
		return paymentInterface.forDealerPayments(dealer_id);
	}

	public ResponseEntity<ReceiptDto> getReceipt(Integer paymentId) {
		return paymentInterface.getReceipt(paymentId);
	}

	public ResponseEntity<List<String>> getAllEmails() {
		List<String> emails = dealerRepo.getAllEmails();
		
		if(!emails.isEmpty())
		{
			return new ResponseEntity<>(emails,HttpStatus.OK);
		}
		
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<List<ReviewDto>> getReviews(int crop_id) {
		
		return dealerInterface.allReviewsByCropId(crop_id);
	}

	public ResponseEntity<Map<String, Integer>> getIdByEmail(String email) {
		Optional<Dealer> dealer = dealerRepo.findByEmail(email);
		Map<String, Integer> response = new HashMap<>();
	    if (dealer != null) {
	    	
	    	 response.put("id", dealer.get().getDealer_id());
	        return ResponseEntity.ok(response);
	    } else {
	    	response.put("id", 0);
	    	return ResponseEntity.ok(response);
	    }
	}
	

	



	

}
