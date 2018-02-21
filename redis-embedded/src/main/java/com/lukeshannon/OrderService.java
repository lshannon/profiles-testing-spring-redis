package com.lukeshannon;

import org.springframework.stereotype.Service;

@Service
public class OrderService {
	
	private OrderRepo orderRepo;

	public OrderService(OrderRepo orderRepo) {
		this.orderRepo = orderRepo;
	}
	
	public Order saveOrder(OrderSubmission orderSubmission) {
		Order order = new Order();
		for (String productId : orderSubmission.getItems().keySet()) {
			order.getItems().add(new Transaction(productId,orderSubmission.getItems().get(productId)));
		}
		order.setTransactionDate(orderSubmission.getTransactionDate());
		order.setCustomerId(orderSubmission.getCustomerId());
		Order savedOrder = orderRepo.save(order);
		return savedOrder;
	}
	
	public Order getOrder(Long id) {
		return orderRepo.findOne(id);
	}
	
}
