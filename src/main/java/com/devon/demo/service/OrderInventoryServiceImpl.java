package com.devon.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import com.devon.demo.config.MessageConfiguration;
import com.devon.demo.messaging.MessageSender2;
import com.devon.demo.model.InventoryResponse;
import com.devon.demo.model.Order;

@Service("orderInventoryService")
@Conditional(MessageConfiguration.class)
public class OrderInventoryServiceImpl implements OrderInventoryService{
	static final Logger LOG = LoggerFactory.getLogger(OrderInventoryServiceImpl.class);
	
	
	@Autowired
	MessageSender2 messageSender2;
	
	@Override
	public void processOrder(Order order) {
		
		//Perform any business logic.
		
		InventoryResponse response = prepareResponse(order);
		LOG.info("Inventory : sending order confirmation {}", response);
		messageSender2.sendMessage(response);
	}
	
	private InventoryResponse prepareResponse(Order order){
		InventoryResponse response = new InventoryResponse();
		response.setOrderId(order.getOrderId());
		response.setReturnCode(200);
		response.setComment("Order Processed successfully");
		return response;
	}

	
	
}
