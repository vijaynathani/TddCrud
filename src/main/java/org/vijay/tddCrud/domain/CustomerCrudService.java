package org.vijay.tddCrud.domain;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.vijay.tddCrud.dao.CustomerDbOperations;
import org.vijay.tddCrud.model.Customer;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CustomerCrudService {

	private Customer c = new Customer();
	private Operations action;
	private Map<String, String[]> inputParameters;
	private String message = "";
	private Utils u = Utils.getInstance();
	private Long lastKey;
	private final CustomerDbOperations custDb;
	
	@Inject
	public CustomerCrudService(CustomerDbOperations custDb) {
		this.custDb = custDb;
	}

	public void process(Map<String, String[]> inputParameters, Long lastKey) {
		this.inputParameters = inputParameters;
		this.lastKey = lastKey;

		action = Operations.getOperationInStringKey(inputParameters);
		performOperation();

	}

	private void performOperation() {
		switch (action) {
		case View:
			performView();
			break;

		case Del:
			performDel();
			break;

		case Add:
			performAdd();
			break;

		case Chg:
			performChg();
			break;

		default:
			message = "Unknown operation";
		}
	}

	private void performView() {
		if (setNumber() && readCustomer())
			message = "Customer retrieved successfully";

	}

	private void performDel() {
		if (!setNumber() || !recordViewedEarlier() || !readCustomer()
				|| !deleteCustomer())
			return;
		message = "record deleted";
	}

	private void performAdd() {
		if (!setNumber() || !setName() || !setAddress() || !setMobileNo()
				|| !addRecord())
			return;
		message = "Add performed";
	}

	private void performChg() {
		if (!setNumber() || !recordViewedEarlier() || !setName()
				|| !setAddress() || !setMobileNo() || !chgRecord())
			return;
		message = "Chg performed";
	}

	private boolean readCustomer() {
		if (custDb.checkCustomerNumber(c)) {
			c = custDb.getLastCustomer();
			return true;
		}
		message = "Customer number not found";
		return false;
	}

	private boolean recordViewedEarlier() {
		if (c.getNumber().equals(lastKey))
			return true;
		message = "View record before change or delete";
		return false;
	}

	private boolean addRecord() {
		if (custDb.checkCustomerNumber(c)) {
			message = "Customer number already present";
			return false;
		}
		custDb.addCustomer(c);
		return true;
	}

	private boolean chgRecord() {
		if (custDb.checkCustomerNumber(c)) {
			Customer cust = custDb.getLastCustomer();
			cust.copyFields(c);
			return true;
		}
		message = "Customer not found";
		return false;
	}

	boolean setNumber() {
		String number = u.findByKey(inputParameters, Customer.numberField);

		if (!u.isValidNumeric(number)) {
			message = "Cusomter number should be numeric";
			return false;
		}
		if (Long.valueOf(number) <= 0L) {
			message = "Customer number not entered or negative";
			return false;
		}
		c.setNumber(Long.valueOf(number));
		return true;
	}

	boolean setName() {
		String name = u.findByKey(inputParameters, Customer.nameField);

		if (!u.isValidLength(name, Customer.nameScreenLength)) {
			message = "Name not given or name too long";
			return false;
		}
		c.setName(name);
		return true;
	}

	boolean setAddress() {
		String address = u.findByKey(inputParameters, Customer.addressField);

		if (!u.isValidLength(address, Customer.addressLength)) {
			message = "Address not given or name too long";
			return false;
		}
		c.setAddress(address);
		return true;
	}

	boolean setMobileNo() {
		String mobile = u.findByKey(inputParameters, Customer.mobileField);

		if (!u.isValidLength(mobile, Customer.mobileScreenLength)) {
			message = "Mobile number not given or too long";
			return false;
		}
		c.setMobile(mobile);
		return true;

	}

	private boolean deleteCustomer() {
		custDb.deleteCustomer();
		return true;
	}

	public String getMessage() {
		return message;
	}

	public Customer getCustomer() {
		return c;
	}
}
