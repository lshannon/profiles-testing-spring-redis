package com.lukeshannon;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {
	
	@LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void testCrud() {
    		//post a submission
    		OrderSubmission submission = new OrderSubmission();
    		submission.setCustomerId("Test Customer");
    		submission.setTransactionDate(new Date());
    		submission.getItems().put("Item A", 3);
    		submission.getItems().put("Item B", 1);
    		submission.getItems().put("Item C", 5);
    		HttpEntity<OrderSubmission> request = new HttpEntity<>(submission);
    		URI location = restTemplate.postForLocation("/order", request);
    		//test submission was successful
    		assertThat(location, notNullValue());
    		//try getting created resource
    		ResponseEntity<Order> response = restTemplate.getForEntity(location.toString(), Order.class);
    		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
    }

}
