package org.vijay.tddCrud.domain;

import static org.vijay.tddCrud.domain.Operations.Add;
import static org.vijay.tddCrud.domain.Operations.Chg;
import static org.vijay.tddCrud.domain.Operations.Del;
import static org.vijay.tddCrud.domain.Operations.View;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.vijay.tddCrud.model.Customer;
import org.vijay.tddCrud.dao.CustomerDbOperations;

public class CustomerCrudServiceTest {

	private CustomerCrudService cv;
	private final Map<String, String[]> parameters = new HashMap<String, String[]>();
	private final Customer getLastCustomerReturn = new Customer();
	private Customer checkCustomerNumberParameter,
	        addCustomerParameter;
	private boolean checkCustomerNumberReturn = true,
	        getLastCustomerCalled = false, deleteCustomerCalled;
	private static Long id1 = 25L, number = 10L, number1 = 30L;
	private static String name = "n1", name1 = "name25",
	        address = "a1", address1 = "addr25", mobile = "123",
	        mobile1 = "255";

	class MockDbOperations implements CustomerDbOperations {

		public void addCustomer(Customer c) {
			addCustomerParameter = c;
		}

		public boolean checkCustomerNumber(Customer c) {
			checkCustomerNumberParameter = c;
			return checkCustomerNumberReturn;
		}

		public void deleteCustomer() {
			deleteCustomerCalled = true;
		}

		public Customer getLastCustomer() {
			getLastCustomerCalled = true;
			return getLastCustomerReturn;
		}
	}

	private String[] convertToArray(String value) {
		return new String[] { value };
	}

	private void addOperationToParameters(Operations o) {
		parameters.put(o.value(), null);

	}

	private void addNumberToParameters(Long number) {
		parameters.put(Customer.numberField, convertToArray(""
		        + number));
	}

	private void addNumberToParameters(String number) {
		parameters.put(Customer.numberField, convertToArray(number));
	}

	private void addAddressToParameters(String address) {
		parameters
		        .put(Customer.addressField, convertToArray(address));
	}

	private void addNameToParameters(String name) {
		parameters.put(Customer.nameField, convertToArray(name));
	}

	private void addMobileToParameters(String mobile) {
		parameters.put(Customer.mobileField, convertToArray(mobile));
	}

	private void addValuesToParameter(Operations o) {
		addOperationToParameters(o);
		addNumberToParameters(number);
		addNameToParameters(name);
		addAddressToParameters(address);
		addMobileToParameters(mobile);
	}

	private void callValidator() {
		callValidator(0L);
	}

	private void callValidator(Long number) {
		cv = new CustomerCrudService(new MockDbOperations());
		cv.process(parameters, number);
	}

	public static String getRandomString(int length) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < length; i++)
			sb.append('a');
		return sb.toString();
	}

	@Test
	public void ViewTestRecordRead() {
		addValuesToParameter(View);
		callValidator();
		assertEquals(number, checkCustomerNumberParameter.getNumber());
		assertTrue(getLastCustomerCalled);
	}

	@Test
	public void ViewTestRecordAbsent() {
		addValuesToParameter(View);
		checkCustomerNumberReturn = false;
		callValidator();
		assertFalse(getLastCustomerCalled);
	}

	@Test
	public void deleteTestRightData() {
		addValuesToParameter(Del);
		callValidator(number);
		assertTrue(deleteCustomerCalled);
	}

	@Test
	public void deleteTestNumberInvalid() {
		addValuesToParameter(Del);
		addNumberToParameters("abc");
		callValidator();
		assertFalse(deleteCustomerCalled);
	}

	@Test
	public void deleteTestRecordNotViewed() {
		addValuesToParameter(Del);
		callValidator();
		assertFalse(deleteCustomerCalled);
	}

	@Test
	public void deleteTestRecordNotPresent() {
		addValuesToParameter(Del);
		checkCustomerNumberReturn = false;
		callValidator(number);
		assertFalse(deleteCustomerCalled);
	}

	private void setCustomerValues(Customer c) {
		c.setId(id1);
		c.setNumber(number1);
		c.setName(name1);
		c.setAddress(address1);
		c.setMobile(mobile1);
	}

	private Customer giveCustomer() {
		return new Customer(number, name, address, mobile);
	}

	private Customer giveCustomer1() {
		return new Customer(number1, name1, address1, mobile1);
	}

	private void ensureCustomerCopied() {
		assertEquals(giveCustomer(), getLastCustomerReturn);
	}

	private void ensureCustomerNotCopied() {
		assertEquals(giveCustomer1(), getLastCustomerReturn);
	}

	@Test
	public void changeTestRightData() {
		addValuesToParameter(Chg);
		setCustomerValues(getLastCustomerReturn);
		callValidator(number);
		assertEquals(number, checkCustomerNumberParameter.getNumber());
		assertEquals(id1, getLastCustomerReturn.getId());
		ensureCustomerCopied();
		assertFalse(deleteCustomerCalled);
	}

	@Test
	public void changeTestRecordAbsent() {
		addValuesToParameter(Chg);
		setCustomerValues(getLastCustomerReturn);
		checkCustomerNumberReturn = false;
		callValidator(number);
		assertEquals(number, checkCustomerNumberParameter.getNumber());
		assertEquals(id1, getLastCustomerReturn.getId());
		ensureCustomerNotCopied();
		assertFalse(deleteCustomerCalled);
	}

	private void ensureCustomerNotRead() {
		assertNull(checkCustomerNumberParameter);
	}

	@Test
	public void changeTestNumberInvalid() {
		addValuesToParameter(Chg);
		addNumberToParameters("abc");
		callValidator();
		ensureCustomerNotRead();
		assertFalse(deleteCustomerCalled);
	}

	@Test
	public void changeTestRecordNotViewed() {
		addValuesToParameter(Chg);
		callValidator();
		ensureCustomerNotRead();
		assertFalse(deleteCustomerCalled);
	}

	@Test
	public void changeTestNameNotGiven() {
		addValuesToParameter(Chg);
		addNameToParameters("");
		callValidator(number);
		ensureCustomerNotRead();
		assertFalse(deleteCustomerCalled);
	}

	@Test
	public void changeTestAddressNotGiven() {
		addValuesToParameter(Chg);
		addAddressToParameters("");
		callValidator(number);
		ensureCustomerNotRead();
		assertFalse(deleteCustomerCalled);
	}

	@Test
	public void changeTestMobileNotGiven() {
		addValuesToParameter(Chg);
		addMobileToParameters("");
		callValidator(number);
		ensureCustomerNotRead();
		assertFalse(deleteCustomerCalled);
	}

	// =========================================

	@Test
	public void addTestRightData() {
		addValuesToParameter(Add);
		checkCustomerNumberReturn = false;
		callValidator();
		assertEquals(number, checkCustomerNumberParameter.getNumber());
		assertEquals(giveCustomer(), addCustomerParameter);
		assertFalse(deleteCustomerCalled);
	}

	private void ensureNoAddPerformed() {
		assertNull(addCustomerParameter);

	}

	@Test
	public void addTestRecordDuplicate() {
		addValuesToParameter(Add);
		callValidator(number);
		assertEquals(number, checkCustomerNumberParameter.getNumber());
		ensureNoAddPerformed();
	}

	@Test
	public void addTestNumberInvalid() {
		addValuesToParameter(Add);
		addNumberToParameters("abc");
		callValidator();
		ensureNoAddPerformed();
	}

	@Test
	public void addTestNameNotGiven() {
		addValuesToParameter(Add);
		addNameToParameters("");
		callValidator();
		ensureNoAddPerformed();
	}

	@Test
	public void addTestAddressNotGiven() {
		addValuesToParameter(Add);
		addAddressToParameters("");
		callValidator();
		ensureNoAddPerformed();
	}

	@Test
	public void addTestMobileNotGiven() {
		addValuesToParameter(Add);
		addMobileToParameters("");
		callValidator();
		ensureNoAddPerformed();
	}

	private void setupNumber(String number) {
		addValuesToParameter(Add);
		addNumberToParameters(number);
		callValidator();
	}

	@Test
	public void checkNumberAlphaNumeric() {
		setupNumber("abc");
		assertFalse(cv.setNumber());
	}

	@Test
	public void checkNumberEmpty() {
		setupNumber("");
		assertFalse(cv.setNumber());
	}

	@Test
	public void checkNumberZero() {
		setupNumber("0");
		assertFalse(cv.setNumber());
	}

	@Test
	public void checkNumberNegative() {
		setupNumber("-1");
		assertFalse(cv.setNumber());
	}

	@Test
	public void checkNumberValid() {
		setupNumber("52");
		assertTrue(cv.setNumber());
	}

	private void setupName(String name) {
		addValuesToParameter(Add);
		addNameToParameters(name);
		callValidator();
	}

	@Test
	public void checkNameEmpty() {
		setupName("");
		assertFalse(cv.setName());
	}

	@Test
	public void checkNameTooLong() {
		setupName(getRandomString(Customer.nameScreenLength + 1));
		assertFalse(cv.setName());
	}

	@Test
	public void checkNameMaxLength() {
		setupName(getRandomString(Customer.nameScreenLength));
		assertTrue(cv.setName());
	}

	@Test
	public void checkNameSomeValue() {
		setupName(getRandomString(3));
		assertTrue(cv.setName());
	}

	private void setupAddress(String address) {
		addValuesToParameter(Add);
		addAddressToParameters(address);
		callValidator();
	}

	@Test
	public void checkAddressEmpty() {
		setupAddress("");
		assertFalse(cv.setAddress());
	}

	@Test
	public void checkAddressTooLong() {
		setupAddress(getRandomString(Customer.addressLength + 1));
		assertFalse(cv.setAddress());
	}

	@Test
	public void checkAddressMaxLength() {
		setupAddress(getRandomString(Customer.addressLength));
		assertTrue(cv.setAddress());
	}

	@Test
	public void checkAddressSomeValue() {
		setupAddress(getRandomString(3));
		assertTrue(cv.setAddress());
	}

	private void setupMobile(String mobile) {
		addValuesToParameter(Add);
		addMobileToParameters(mobile);
		callValidator();
	}

	@Test
	public void checkMobileEmpty() {
		setupMobile("");
		assertFalse(cv.setMobileNo());
	}

	@Test
	public void checkMobileTooLong() {
		setupMobile(getRandomString(Customer.mobileScreenLength + 1));
		assertFalse(cv.setMobileNo());
	}

	@Test
	public void checkMobileMaxLength() {
		setupMobile(getRandomString(Customer.mobileScreenLength));
		assertTrue(cv.setMobileNo());
	}

	@Test
	public void checkMobileSomeValue() {
		setupMobile(getRandomString(3));
		assertTrue(cv.setMobileNo());
	}
}
