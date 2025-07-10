package com.notification.consumer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.notification.config.RabbitConfig;
import com.notification.feign.NotificationDealerInterface;
import com.notification.feign.NotificationFarmerInterface;
import com.notification.model.CropDto;
import com.notification.model.NotificationPayload;
import com.notification.model.NotifyFarmer;

import org.springframework.mail.javamail.JavaMailSender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;


@Component
public class UserConsumer {
	
	 @Value("${notification.queue}")
	 private String queue;
	 
	 @Autowired
	 private JavaMailSender mailSender;
	 
	 @Autowired
	 NotificationDealerInterface dealerInterface;
	 
	 @Autowired
	 NotificationFarmerInterface farmerInterface;

	 
	 @RabbitListener(queues = RabbitConfig.QUEUE)
	    public void consumeMessageFromQueue(CropDto crop) {
		
		   String message = "New crop is published Crop Name : "+crop.getCrop_name()+
			" Crop Type : "+crop.getCrop_type()+" Available Quantity : "+crop.getQuantity_in_kg()+
			" Price per kg : " + crop.getPrice_per_kg()+" Rs "+" Check App/Website for more details";
		   
		   List<String> emails = new ArrayList<>();
		  // emails.add("tushar200392@gmail.com");
		 //  emails.add("koraketejas600@gmail.com");
		  // emails.add("kshitijmulay411@gmail.com");
		   
		   emails=dealerInterface.getAllEmails().getBody();
		   
		   NotificationPayload payload = new NotificationPayload();
		   
		   payload.setSubject("!!!!! Crop Published !!!!!");
		   payload.setTo(emails);
		   payload.setBody(message);
		   
		   notifyDealers(payload);
			 
	        System.out.println("Message recieved from queue : " + crop);
	    }
	 
	
		public ResponseEntity<String> notifyDealers(NotificationPayload payload) {
			try {
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, true);

				helper.setSubject(payload.getSubject());
				helper.setText(payload.getBody(), true);
				helper.setFrom("tushark2039@gmail.com");
				for (String email : payload.getTo()) {
					helper.addBcc(email);
				}

				mailSender.send(message);
				return ResponseEntity.ok("Notification sent to all dealers");
			} catch (MessagingException e) {
				return ResponseEntity.status(500).body("Failed to send notification: " + e.getMessage());
			}
		}
		
		
		 @RabbitListener(queues = RabbitConfig.BOOKINGQUEUE)
		    public void consumeBookingMessage(NotifyFarmer notify) {
			 
			   String message ="Booked by (dealer name) : "+notify.getDealerName()+ "Crop Name : "+notify.getCropName()+
				" Crop Type : "+notify.getCropType()+"Quantity booked : "+notify.getBookedQuantity()+" Rs "+" Check App/Website for more details";
			   
			   List<String> emails = new ArrayList<>();
			  emails.add("tushar200392@gmail.com");
			  //  emails.add("koraketejas600@gmail.com");
			  // emails.add("kshitijmulay411@gmail.com");
			   
			   System.out.println(notify);
			   
//			   String farmerEmail = farmerInterface.emailByCropId(notify.getCrop_id()).getBody();
//			   emails.add(farmerEmail );
			   
			   
			   NotificationPayload payload = new NotificationPayload();
			   
			   payload.setSubject("!!!!! Your crop is booked !!!!!");
			   payload.setTo(emails);
			   payload.setBody(message);
			   
			   notifyFarmer(payload);
				 
		        System.out.println("Message recieved from queue : " + notify);
		    }
		 
		
			public ResponseEntity<String> notifyFarmer(NotificationPayload payload) {
				try {
					MimeMessage message = mailSender.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(message, true);

					helper.setSubject(payload.getSubject());
					helper.setText(payload.getBody(), true);
					helper.setFrom("tushark2039@gmail.com");
					for (String email : payload.getTo()) {
						helper.addBcc(email);
					}

					mailSender.send(message);
					return ResponseEntity.ok("Notification sent to farmer");
				} catch (MessagingException e) {
					return ResponseEntity.status(500).body("Failed to send notification: " + e.getMessage());
				}
			}


	 
	 

}
