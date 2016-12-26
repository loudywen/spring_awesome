package com.devon.demo.service;

import com.devon.demo.model.InventoryResponse;
import com.devon.demo.model.Order;
import java.util.Map;

public interface OrderService {
  public void sendOrder(Order order);

  public void updateOrder(InventoryResponse response);

  public Map<String, Order> getAllOrders();
}
