package org.vijay.tddCrud.acceptanceTests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.vijay.tddCrud.common.BaseTest;
import org.vijay.tddCrud.common.Check;
import org.vijay.tddCrud.common.TestConfiguration;
import org.vijay.tddCrud.domain.CustomerCrudServiceTest;
import org.vijay.tddCrud.model.Customer;

/**
 * Acceptance / System / Functional Testing Tests decided by Developer / Tester
 * / Customer
 * <ul>
 * <li>Test1 :
 * 
 * <pre>
 * Add a customer with values 
 *      number = 50
 *      name = comp50
 *      address = myStreet50
 *      mobile  = 50
 * View the information and verify that it is same.
 * Change the name to comp51, address to mystreet51 and mobile to 51.
 * View the information to verify the change is made.
 * Delete this Customer and ensure View does not find it.
 * </pre>
 * 
 * <li>Test 2: Try to change an existing customer without viewing it. Ensure we
 * get an error message.
 * 
 * <li>Test 3: Try to delete an existing customer without viewing it. Ensure we
 * get an error message.
 * 
 * <li>Test 4: Try to view a non existing customer. Ensure we get an error
 * message.</p>
 * </ul>
 */
// These tests are usually given to the Customer /
// Testers / Analysts.Hence they are written in simple HTML.
// JavaDoc tool can extract the above comments into HTML
// pages for review.
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfiguration.class)
public class CustomerFunctionalTest extends BaseTest {

	private final long rec_number = 50L;
	private final Customer originalCustomer = new Customer(rec_number,
			"comp50", "myStreet50", "50"), newCustomer = new Customer(
			rec_number, "com51", "myStreet51", "51");
	CustomerCrudServiceFacade csf = new CustomerCrudServiceFacade(this);

	@Test
	public void Test1_HappyPathTesting() {
		deleteExistingRecordIfAny();
		csf.addCustomer(originalCustomer);
		assertEquals(originalCustomer,
				csf.viewCustomer(originalCustomer.getNumber()));
		csf.changeCustomer(newCustomer);
		assertEquals(newCustomer,
				csf.viewCustomer(originalCustomer.getNumber()));
		csf.deleteCustomer(originalCustomer);
		Customer o = new Customer();
		o.setNumber(rec_number);
		assertEquals(o, csf.viewCustomer(originalCustomer.getNumber()));
	}

	@Test
	public void Test2_changeCustomerWithoutViewing() {
		Check.EnsureTestMethodPresent(CustomerCrudServiceTest.class, "changeTestRecordNotViewed");
	}

	@Test
	public void Test3_deleteCustomerWithoutViewing() {
		Check.EnsureTestMethodPresent(CustomerCrudServiceTest.class, "deleteTestRecordNotViewed");
	}

	@Test
	public void Test4_viewNonExisitingCustomer() {
		Check.EnsureTestMethodPresent(CustomerCrudServiceTest.class, "ViewTestRecordAbsent");
	}

	private void deleteExistingRecordIfAny() {
		// Setup the data using dbUnit or by giving
		// individual SQL statements
		session.createSQLQuery(
				"delete from Customer where number ="
						+ originalCustomer.getNumber()).executeUpdate();
		commitTransactionAndBeginNewTransaction();
	}
}
