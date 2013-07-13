package org.vijay.tddCrud.dao;

import org.vijay.tddCrud.model.Customer;

public interface CustomerDbOperations {
	boolean checkCustomerNumber(Customer c);

	Customer getLastCustomer();

	void deleteCustomer();

	void addCustomer(Customer c);
}
