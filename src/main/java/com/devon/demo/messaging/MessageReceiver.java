package com.devon.demo.messaging;


import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.devon.demo.config.MessageConfiguration;
import com.devon.demo.model.InventoryResponse;
import com.devon.demo.model.Order;
import com.devon.demo.service.OrderInventoryService;
import com.devon.demo.service.OrderService;

@Component
@Conditional(MessageConfiguration.class)

public class MessageReceiver {
	static final Logger			LOG						=
			LoggerFactory.getLogger(MessageReceiver.class);

	private static final String	ORDER_RESPONSE_QUEUE	= "Test2";
	private static final String	ORDER_REQUEST_QUEUE		= "Test1";

	@Autowired
	OrderService				orderService;

	@Autowired
	OrderInventoryService		orderInventoryService;

	@JmsListener(destination = ORDER_RESPONSE_QUEUE)
	public void receiveMessage(final Message<InventoryResponse> message) throws JMSException {
		LOG.info(
				"+++++++++++++++++++++++++Client Received Confirmation++++++++++++++++++++++++++++");
		MessageHeaders headers = message.getHeaders();
		LOG.info("Application : headers received : {}", headers);

		InventoryResponse response = message.getPayload();
		LOG.info("Application : response received : {}", response);

		orderService.updateOrder(response);
		LOG.info(
				"++++++++++++++++++++++++++Client Received Confirmation+++++++++++++++++++++++++++");
	}


	@JmsListener(destination = ORDER_REQUEST_QUEUE)
	public void receiveOrderMessage(final Message<Order> message) throws JMSException {
		LOG.info("---------------------Provider Received Order-------------------------------");
		MessageHeaders headers = message.getHeaders();
		LOG.info("Application : headers received : {}", headers);
		Order order = message.getPayload();
		LOG.info("Application : product : {}", order);
		orderInventoryService.processOrder(order);
		LOG.info("---------------------Provider Processed Order-------------------------------");
	}
}
