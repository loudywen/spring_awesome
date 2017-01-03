package com.devon.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.devon.demo.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	List<Customer> findByLastName(String lastName);
	Customer findByFirstNameAndLastName(String firstName, String lastName);
	
}