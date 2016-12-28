package com.devon.demo.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import com.devon.demo.config.MessageConfiguration;
import com.devon.demo.model.Order;
@Service("orderRepository")
@Conditional(MessageConfiguration.class)
public class OrderRepositoryImpl implements OrderRepository {

	private final Map<String, Order> orders = new ConcurrentHashMap<String, Order>();
    
    @Override
    public void putOrder(Order order) {
        orders.put(order.getOrderId(), order);
    }
 
    @Override
    public Order getOrder(String orderId) {
        return orders.get(orderId);
    }
 
    public Map<String, Order> getAllOrders(){
        return orders;
    }

}
