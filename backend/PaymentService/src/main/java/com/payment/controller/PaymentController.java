package com.payment.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payment.model.Payment;
import com.payment.model.Receipt;
import com.payment.service.PaymentService;
import com.razorpay.RazorpayException;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/payment")
@Tag(name="Payment APIs",description="Crop Deal - Payment Methods")
public class PaymentController {
	
	
	@Autowired
    private PaymentService paymentService;
	
	

	//1  Make payment	
    @PostMapping("/make-payement")
    public ResponseEntity<String> makePayment(@Valid @RequestBody Payment payment) {
    	 return paymentService.makePayment(payment);
     
    }
    
    //2 List Of All Payments
    @GetMapping("/all-payments")
    public ResponseEntity<List<Payment>> allPayments() {
        return paymentService.allPayments();
    }
    
    //3 Get Payment Details By its id
    @GetMapping("/{id}") 
    public ResponseEntity<Payment> getPaymentById(@PathVariable int id) {
        return paymentService.getPaymentById(id);
    }
    
    //4 List Of Payments for farmers
    @GetMapping("/for-farmer")
    public ResponseEntity<List<Payment>> forFarmerPayments(@RequestParam int farmer_id) {
        return paymentService.forFarmerPayments(farmer_id);
    }
    
    //5 List Of Payments for dealers
    @GetMapping("/for-dealer")
    public ResponseEntity<List<Payment>> forDealerPayments(@RequestParam int dealer_id) {
        return paymentService.forDealerPayments(dealer_id);
    }

    //6 get amount to pay
    @GetMapping("/amount-to-pay/{book_id}")
    public ResponseEntity<?> getAmountToBePaid(@PathVariable int book_id) {
        return paymentService.getAmountToBePaid(book_id);
    }
   
   // ------------------------- Receipt generation ----------------------
    
    @GetMapping("/receipt/{paymentId}")
    public ResponseEntity<Receipt> getReceipt(@PathVariable Integer paymentId) {
        return paymentService.getReceipt(paymentId);
    }
    
    
    //---------------razorpay-------------------------
    
    @PostMapping("/order")
    public String createOrder(@RequestParam int amount,@RequestParam String id)
    {
    	try {
			return paymentService.createOrder(amount, id);
		} catch (RazorpayException e) {
			e.printStackTrace();
		}
		return "Payment success";
    }
    
    

}
