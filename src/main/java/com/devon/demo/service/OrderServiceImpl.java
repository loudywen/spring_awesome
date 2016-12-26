package com.devon.demo.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devon.demo.messaging.MessageSender;
import com.devon.demo.model.InventoryResponse;
import com.devon.demo.model.Order;
import com.devon.demo.model.OrderStatus;
import com.devon.demo.util.BasicUtil;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

	private static final Logger	LOG	= LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	MessageSender				messageSender;

	@Autowired
	OrderRepository				orderRepository;

	@Override
	public void sendOrder(Order order) {
		LOG.info(
				"++++++++++++++++++++Client Send Order To Provider +++++++++++++++++++++++++++++++++");
		order.setOrderId(BasicUtil.getUniqueId());
		order.setStatus(OrderStatus.CREATED);
		orderRepository.putOrder(order);
		LOG.info("Application : sending order request: {}", order);
		messageSender.sendMessage(order);
		LOG.info(
				"++++++++++++++++++++Client Send Order To Provider +++++++++++++++++++++++++++++++++");

	}

	@Override
	public void updateOrder(InventoryResponse response) {
		Order order = orderRepository.getOrder(response.getOrderId());
		if (response.getReturnCode() == 200) {
			order.setStatus(OrderStatus.CONFIRMED);
		} else if (response.getReturnCode() == 300) {
			order.setStatus(OrderStatus.FAILED);
		} else {
			order.setStatus(OrderStatus.PENDING);
		}
		orderRepository.putOrder(order);
	}

	@Override
	public Map<String, Order> getAllOrders() {
		return orderRepository.getAllOrders();
	}
}
