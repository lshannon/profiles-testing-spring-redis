package com.lukeshannon;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
public class MockServiceControllerTest {
	
	@Mock
    OrderService orderService;	

	@InjectMocks
	OrderController orderController;
    
    private MockMvc mockMvc;
    
    @Before
    public void setup() {
    		MockitoAnnotations.initMocks(this);
    		this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }
    

    @Test
    public void testCrud() throws Exception {
    		//post a submission
    		OrderSubmission submission = new OrderSubmission();
    		submission.setCustomerId("Test Customer");
    		submission.setId(Long.valueOf(1));
    		submission.setTransactionDate(new Date());
    		submission.getItems().put("Item A", 3);
    		submission.getItems().put("Item B", 1);
    		submission.getItems().put("Item C", 5);
    		//try the post
    		mockMvc.perform(
                post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(submission)))
                .andExpect(status().is2xxSuccessful());
    		//create the Mock we want to return
    		Order mockOrder = new Order();
    		mockOrder.setId(Long.valueOf(1));
    		mockOrder.setCustomerId("Test Customer");
    		//configure the Mock Service to return our Mock Data
    		Mockito.when(orderService.getOrder(Long.valueOf(1))).thenReturn(mockOrder);
    		//test the get
    		 mockMvc.perform(get("/order/{id}", 1))
             .andExpect(status().isOk())
             .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
             .andExpect(jsonPath("$.id", is(1)))
             .andExpect(jsonPath("$.customerId", is("Test Customer")));
    }
    
    /*
     * converts a Java object into JSON representation
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	
	

}
