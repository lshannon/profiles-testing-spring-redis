/**
 * 
 */
package com.lukeshannon;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author lshannon
 *
 */
@RestController
public class OrderController {
	
	private static final Logger log = LoggerFactory.getLogger(OrderController.class);
	private OrderRepo orderRepo;

	public OrderController(OrderRepo orderRepo) {
		this.orderRepo = orderRepo;
	}
	
	@GetMapping("/order/{id}")
	public ResponseEntity<Order> getOrder(@PathVariable("id") Long id) {
		Order order = orderRepo.findOne(id);
		if (order != null) {
			return ResponseEntity.ok(order);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/order")
	public ResponseEntity<Void> setRating(@RequestBody OrderSubmission orderSubmission) {
		Order order = new Order();
		for (String productId : orderSubmission.getItems().keySet()) {
			order.getItems().add(new Transaction(productId,orderSubmission.getItems().get(productId)));
		}
		order.setTransactionDate(orderSubmission.getTransactionDate());
		order.setCustomerId(orderSubmission.getCustomerId());
		Order savedOrder = orderRepo.save(order);
		if (savedOrder == null) {
			log.error("Unable to save: " + orderSubmission);
			return ResponseEntity.noContent().build();
		}
		URI entityLocation = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedOrder.getId()).toUri();
		return ResponseEntity.created(entityLocation).build();
	}
	
	
	

}
