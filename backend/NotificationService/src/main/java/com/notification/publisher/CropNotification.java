package com.notification.publisher;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.notification.config.RabbitConfig;
import com.notification.model.CropDto;
import com.notification.model.NotifyFarmer;


@RestController
@RequestMapping("/crop")
public class CropNotification {

	 @Autowired
	 private RabbitTemplate template;
	 
	 @Value("${notification.exchange}")
	 private String exchange;

    @Value("${notification.routing-key}")
    private String routingKey;
    
    
    @Value("${booking.queue}")
    private String bookingqueue;

    @Value("${booking.routing-key}")
    private String routeBookKey;
    
	@PostMapping("/published/now")
    public String publishedCrop(@RequestBody CropDto crop) {
      template.convertAndSend(exchange, routingKey, crop);
        return "Crop Notification Send Successfully !!";
    }
	
	@PostMapping("/booked")
    public String bookedCrop(@RequestBody NotifyFarmer notify) {
	
        template.convertAndSend(exchange, routeBookKey, notify);
        return "Crop booked Notification Send Successfully !!";
    }
}
