package com.lukeshannon;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
class OrderSubmission {
	private Long id;
	private String customerId;
	private Map<String, Integer> items = new HashMap<String, Integer>();
	private Date transactionDate;
}