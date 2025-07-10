package com.payment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.payment.feign.PaymentCropInterface;
import com.payment.feign.PaymentDealerInterface;
import com.payment.feign.PaymentFarmerInterface;
import com.payment.model.Payment;
import com.payment.model.Receipt;
import com.payment.repo.PaymentRepo;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.payment.model.BookingDto;
import com.payment.model.CropDto;
import com.payment.model.DealerDto;
import com.payment.model.FarmerDto;

@Service
public class PaymentService {
	
	 @Autowired
	 private PaymentRepo paymentRepo;
	 
	 @Autowired
	 PaymentDealerInterface dealer_interface;
	 
	 @Autowired
	 PaymentFarmerInterface farmer_interface;
	 
	 @Autowired
	 PaymentCropInterface crop_interface;
	 
	 @Value("${razorpay.api.key}")
	 private String apiKey;
	 
	 @Value("${razorpay.api.secret}")
	 private String apiSecret;
	 
	 
	 

    public ResponseEntity<String>  makePayment(Payment payment)  {
    	
    	List<BookingDto> booking_list = dealer_interface.allBookings().getBody();
    	Payment saved = null;
    	String razorOrder = null;
    	
    	for(BookingDto b : booking_list)
    	{
    		if(b.getDealerId()== payment.getDealerId() && b.getCropId() == payment.getCropId() && b.getAmount() == payment.getAmount() && b.getStatus().equalsIgnoreCase("book"))
    		{
    		 saved = paymentRepo.save(payment);
    		}
    		
    		
    	}
    	
		if(saved!=null)
		{
			try {
				razorOrder = createOrder(payment.getAmount(),"CP"+payment.getCropId()+payment.getDealerId()+payment.getFarmerId());
    		} catch (RazorpayException e) {
    			
    			e.printStackTrace();
    		}
			return new ResponseEntity<>(razorOrder,HttpStatus.CREATED) ;
		}
		return new ResponseEntity<>("payment not done",HttpStatus.INTERNAL_SERVER_ERROR) ; 
    }

	public ResponseEntity<List<Payment>> allPayments() {
		
		try
		{
			return new ResponseEntity<>(paymentRepo.findAll(),HttpStatus.OK) ;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND) ;
	}
	
	public ResponseEntity<Payment> getPaymentById(int id) {
		try
		{
			Optional<Payment> payment = paymentRepo.findById(id);
			return new ResponseEntity<>(payment.get(),HttpStatus.OK) ;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND) ;
		
	}

	public ResponseEntity<List<Payment>> forFarmerPayments(int farmer_id) {
		try
		{
			return new ResponseEntity<>(paymentRepo.findForFarmer(farmer_id),HttpStatus.OK) ;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND) ;
	}

	public ResponseEntity<List<Payment>> forDealerPayments(int dealer_id) {
		try
		{
			return new ResponseEntity<>(paymentRepo.findForDealer(dealer_id),HttpStatus.OK) ;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND) ;
	}

	public ResponseEntity<String> getAmountToBePaid(int book_id) {
		
		BookingDto booking = null;
		
		booking = dealer_interface.getBookDetails(book_id).getBody().get();
		
		if(booking == null)
		{
			return new ResponseEntity<>("You Don't have any bookings",HttpStatus.NOT_FOUND);
		}
		
		if(!booking.getStatus().equals("book"))
		{
			return new ResponseEntity<>("You Don't have any bookings",HttpStatus.NOT_FOUND);
		}
		
		CropDto crop = crop_interface.getCropDetailsById(booking.getCropId()).getBody().get();
		
		
		return new ResponseEntity<>("You have to pay : "+booking.getAmount()+" to farmer id with : "+crop.getFarmer_id(),HttpStatus.OK);
	}
	

	public ResponseEntity<Receipt> getReceipt(Integer paymentId) {
		Payment payment = paymentRepo.findById(paymentId)
		        .orElseThrow(() -> new RuntimeException("Payment not found"));
		CropDto crop;
		FarmerDto farmer;
		DealerDto dealer;
		
		   
		
		  
		    try {
		    	crop = crop_interface.getCropDetailsById(payment.getCropId()).getBody().get();
		        farmer = farmer_interface.getFarmerProfileById(payment.getFarmerId()).getBody().get();
			    dealer = dealer_interface.getDealerProfileById(payment.getDealerId()).getBody().get();
		    }
		    catch(NoSuchElementException e)
		    {
		    	return new  ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		    }
		    
		    

		    Receipt receipt = new Receipt();
		    receipt.setPaymentId(paymentId);
//		    receipt.setDealerId(payment.getDealerId());
//		    receipt.setFarmerId(payment.getFarmerId());
//		    receipt.setCropId(payment.getCropId());
		    receipt.setAmount(payment.getAmount());
		    receipt.setPaidAt(payment.getPaidAt());
		    receipt.setFarmerName(farmer.getFirst_name()+" "+farmer.getLast_name());
		    receipt.setDealerName(dealer.getFirst_name()+" "+dealer.getLast_name());
		    receipt.setCropName(crop.getCrop_name());
		    receipt.setQuantity(payment.getAmount()/crop.getPrice_per_kg());

		    return ResponseEntity.ok(receipt);
	}
	
	//------------------------razorpay-----------------
	public String createOrder(double amount,String receiptId) throws RazorpayException
	{
		
		RazorpayClient razorpayClient = new RazorpayClient(apiKey,apiSecret);
		
		JSONObject orderRequest = new JSONObject();
		
		orderRequest.put("amount", amount*100);
		orderRequest.put("currency", "INR");
		orderRequest.put("receipt",receiptId);
		
		Order order = razorpayClient.orders.create(orderRequest);
		System.out.println(order);
		
		return order.toString();
		
		
     }


	
	

}
