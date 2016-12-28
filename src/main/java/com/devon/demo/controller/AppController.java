package com.devon.demo.controller;

import com.devon.demo.config.MessageConfiguration;
import com.devon.demo.model.Order;
import com.devon.demo.service.OrderService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Conditional(MessageConfiguration.class)
public class AppController {

	@Autowired
	OrderService orderService;

	@RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
	public String prepareProduct(ModelMap model) {
		return "index";
	}

	@RequestMapping(value = {"/newOrder"}, method = RequestMethod.GET)
	public String prepareOrder(ModelMap model) {
		Order order = new Order();
		model.addAttribute("order", order);
		return "order";
	}

	@RequestMapping(value = {"/newOrder"}, method = RequestMethod.POST)
	public String sendOrder(@Valid Order order, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			return "order";
		}
		orderService.sendOrder(order);
		model.addAttribute("success", "Order for " + order.getProductName() + " registered.");
		return "ordersuccess";
	}

	@RequestMapping(value = {"/checkStatus"}, method = RequestMethod.GET)
	public String checkOrderStatus(ModelMap model) {
		model.addAttribute("orders", orderService.getAllOrders());
		return "orderStatus";
	}
}
