package com.lukeshannon;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RedisHash("order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	
	@Id
	private Long id = new Date().getTime();
	private String customerId;
	private List<Transaction> items = new ArrayList<Transaction>();
	private Date transactionDate;
	

}
