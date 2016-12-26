package com.devon.demo.service;

import java.util.Map;

import com.devon.demo.model.Order;

public interface OrderRepository {
	public void putOrder(Order order);

	public Order getOrder(String orderId);

	public Map<String, Order> getAllOrders();
}
