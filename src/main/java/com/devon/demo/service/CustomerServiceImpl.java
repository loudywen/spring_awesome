package com.devon.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devon.demo.entity.Customer;
import com.devon.demo.repo.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepository cr;

	@Override
	public Iterable<Customer> findAll() {

		return cr.findAll();
	}

	@Override
	public void saveCustomer(Customer customer) {
		cr.save(customer);

	}

	@Override
	public Customer findByFirstNameAndLastName(String firstName, String lastName) {
		Customer c = cr.findByFirstNameAndLastName(firstName, lastName);
		return c;
	}

}
