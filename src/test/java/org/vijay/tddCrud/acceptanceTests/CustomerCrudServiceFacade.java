package org.vijay.tddCrud.acceptanceTests;

import java.util.HashMap;
import java.util.Map;

import org.vijay.tddCrud.common.BaseTest;
import org.vijay.tddCrud.domain.CustomerCrudService;
import org.vijay.tddCrud.domain.Operations;
import org.vijay.tddCrud.model.Customer;
import org.vijay.tddCrud.dao.CustomerDbOperateWithSession;

public class CustomerCrudServiceFacade {
	
	BaseTest b;
		
	public CustomerCrudServiceFacade (BaseTest b) {
		this.b = b;
	}
	
	public void addCustomer(Customer c) {
		new CustomerCrudService(new CustomerDbOperateWithSession(b.session))
			.process(setParameters(Operations.Add, c), 0L);
		b.commitTransactionAndBeginNewTransaction();		
	}
	
	public Customer viewCustomer(long number) {
		Customer c = new Customer();
		c.setNumber(number);
		CustomerCrudService cv = new CustomerCrudService(new CustomerDbOperateWithSession(b.session));
		cv.process(setParameters(Operations.View, c), 0L);
		b.commitTransactionAndBeginNewTransaction();		
		return cv.getCustomer();
	}
	
	public void changeCustomer(Customer newCustomer) {
		new CustomerCrudService(new CustomerDbOperateWithSession(b.session))
			.process(setParameters(Operations.Chg, newCustomer), newCustomer.getNumber());
		b.commitTransactionAndBeginNewTransaction();		
	}

	public void deleteCustomer(Customer c) {
		new CustomerCrudService(new CustomerDbOperateWithSession(b.session))
			.process(setParameters(Operations.Del,c), c.getNumber() );
		b.commitTransactionAndBeginNewTransaction();		
	}

	private String[] convertToArray(String s) {
		return new String[] { s };
	}

	private Map<String, String[]> setParameters(Operations op,
	        Customer c) {
		Map<String, String[]> parameters = new HashMap<String, String[]>();
		parameters.put(op.value(), null);
		parameters.put(Customer.numberField, convertToArray(String
		        .valueOf(c.getNumber())));
		parameters.put(Customer.nameField,
		        convertToArray(c.getName()));
		parameters.put(Customer.addressField, convertToArray(c
		        .getAddress()));
		parameters.put(Customer.mobileField, convertToArray(c
		        .getMobile()));
		return parameters;
	}
}
