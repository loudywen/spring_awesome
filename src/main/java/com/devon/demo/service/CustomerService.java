package com.devon.demo.service;

import com.devon.demo.entity.Customer;

public interface CustomerService {
	Iterable<Customer> findAll();
	void saveCustomer(Customer customer);
	Customer findByFirstNameAndLastName(String firstName, String lastName);
}
