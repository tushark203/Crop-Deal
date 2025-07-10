package com.notification.publisher;


import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.notification.config.RabbitConfig;
import com.notification.model.Order;
import com.notification.model.OrderStatus;


@RestController
@RequestMapping("/order")
public class OrderPublisher {


	    @Value("${notification.exchange}")
	    private String exchange;

	    @Value("${notification.routing-key}")
	    private String routingKey;
	
	 @Autowired
    private RabbitTemplate template;

    @PostMapping("/{restaurantName}")
    public String bookOrder(@RequestBody Order order, @PathVariable String restaurantName) {
        order.setOrderId(UUID.randomUUID().toString());
       
        OrderStatus orderStatus = new OrderStatus(order, "PROCESS", "order placed succesfully in " + restaurantName);
        template.convertAndSend(exchange, routingKey, orderStatus);
        return "Success !!";
    }

}
