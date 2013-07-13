package org.vijay.tddCrud.concordionTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.vijay.tddCrud.acceptanceTests.CustomerCrudServiceFacade;
import org.vijay.tddCrud.common.BaseTest;
import org.vijay.tddCrud.common.ConcordionHelper;
import org.vijay.tddCrud.common.TestConfiguration;
import org.vijay.tddCrud.model.Customer;


//The output will be in temp folder i.e. Contents of variable $TEMP windows variable.
//When run from linux it will be in /tmp (of cygwin) 

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfiguration.class)
public class DemoTest extends BaseTest {
	CustomerCrudServiceFacade csf = new CustomerCrudServiceFacade(this);

	@Test
	public void run() {
		ConcordionHelper.run(this);
	}

	private Customer originalCustomer, newCustomer;

	public void addCustomer(long number, String name, String address,
	        int mobile) {
		originalCustomer = new Customer(number, name, address, ""
		        + mobile);
		deleteExistingRecordIfAny();
		csf.addCustomer(originalCustomer);
	}
	
	public String viewRecord() {
		return originalCustomer.equals(csf.viewCustomer(originalCustomer.getNumber())) ? "same"
		        : "not same";
	}

	public String chgRecord(String name, String address, int mobile) {
		newCustomer = new Customer(originalCustomer.getNumber(),
		        name, address, "" + mobile);
		csf.changeCustomer(newCustomer);
		return newCustomer.equals(csf.viewCustomer(originalCustomer.getNumber())) ? "done"
		        : "not done";
	}
	
	public String delRecord() {
		csf.deleteCustomer(originalCustomer);
		Customer o = new Customer();
		o.setNumber(originalCustomer.getNumber());
		return o.equals(csf.viewCustomer(originalCustomer.getNumber())) ? "not find" : "find (error)";
	}

	private void deleteExistingRecordIfAny() {
		// Setup the data using dbUnit or by giving
		// individual SQL statements
		session.createSQLQuery(
		        "delete from Customer where number ="
		                + originalCustomer.getNumber())
		        .executeUpdate();
		commitTransactionAndBeginNewTransaction();
	}

}