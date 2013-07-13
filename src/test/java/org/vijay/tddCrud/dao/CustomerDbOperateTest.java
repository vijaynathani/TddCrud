package org.vijay.tddCrud.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.vijay.tddCrud.common.BaseTest;
import org.vijay.tddCrud.common.TestConfiguration;
import org.vijay.tddCrud.model.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfiguration.class)
public class CustomerDbOperateTest extends BaseTest {
	CustomerDbOperate sut;
	Customer lastCustomer;

	@Before
	public void setUp() {
		sut = new CustomerDbOperateWithSession(session);
		lastCustomer = (Customer) session
				.createQuery(
						"select c from Customer c where c.number = (select max(number) from Customer)")
				.uniqueResult();
	}

	private Long getLastId() {
		return (Long) session.createQuery("select max(c.id) from Customer c")
				.uniqueResult();
	}

	@Test
	public void addCustomer() {
		Long lastId = getLastId();
		Customer newCust = new Customer();
		newCust.copyFields(lastCustomer);
		newCust.setNumber(newCust.getNumber() + 1);
		sut.addCustomer(newCust);
		assertTrue(lastId < newCust.getId());
	}

	@Test
	public void deleteCustomer() {
		assertTrue(sut.checkCustomerNumber(lastCustomer));
		assertSame(lastCustomer, sut.getLastCustomer());
		sut.deleteCustomer();
		assertFalse(sut.checkCustomerNumber(lastCustomer));
	}
}
