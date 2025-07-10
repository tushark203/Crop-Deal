package com.dealer.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.dealer.model.PaymentDto;
import com.dealer.model.ReceiptDto;



@FeignClient("PAYMENTSERVICE")
public interface DealerPaymentInterface {
	
	@GetMapping("/payment/amount-to-pay/{book_id}")
    public ResponseEntity<String> getAmountToBePaid(@PathVariable int book_id) ;
	
		
    @PostMapping("/payment/make-payement")
    public ResponseEntity<String> makePayment(@RequestBody PaymentDto payment);
    
    @GetMapping("/payment/for-dealer")
    public ResponseEntity<List<PaymentDto>> forDealerPayments(@RequestParam int dealer_id);

    @GetMapping("/payment/receipt/{paymentId}")
    public ResponseEntity<ReceiptDto> getReceipt(@PathVariable Integer paymentId) ;
	

}
