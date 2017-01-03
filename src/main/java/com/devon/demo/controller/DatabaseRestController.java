package com.devon.demo.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devon.demo.entity.Customer;
import com.devon.demo.service.CustomerService;

@RestController
public class DatabaseRestController {
	private Logger log = org.slf4j.LoggerFactory.getLogger(DatabaseRestController.class);
	@Autowired
	private CustomerService cs;

	// @GetMapping("/")
	// public void findAll();

	@PostMapping("/save")
	public String saveCustomer(@RequestParam(value = "fn") String firstName,
			@RequestParam(value = "ln") String lastName) {

		Customer customer = null;
		if (firstName != null && lastName != null) {
			if (cs.findByFirstNameAndLastName(firstName, lastName) == null) {
				customer = new Customer(firstName, lastName);
				cs.saveCustomer(customer);
				return "saved";
			} else {
				return "already there....";
			}

		} else {
			return "fn | ln is null";
		}

	}
}
