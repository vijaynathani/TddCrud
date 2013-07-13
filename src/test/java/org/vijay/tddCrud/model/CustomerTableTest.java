package org.vijay.tddCrud.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.hibernate.type.StandardBasicTypes;
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
public class CustomerTableTest extends BaseTest {
	
	private final Long number1 = 10L;
	private final String name1 = "n1", address1 = "a1",
	        mobile1 = "12";
	Customer c = new Customer(number1, name1, address1, mobile1);

	// Ensure table has atleast 2 records before running
	// these tests.

	private Long getLastCustomerId() {
		Long r = 0L;

		try {
			r = ((Integer) (session
			        .createSQLQuery("select max(c.id) from Customer c")
			        .uniqueResult())).longValue();
		} catch (Exception ignore) {
			System.out.println("Exception:" + ignore);
		}
		return r;
	}

	private Customer getLastCustomer() {
		Long id1 = getLastCustomerId();
		return (Customer) session.get(Customer.class, id1);
	}

	@Test
	public void addRecord() {
		session.save(c);
	}

	@Test
	public void checkPrimaryKeyIncrementedForAdd() {
		Long id1 = getLastCustomerId();
		session.persist(c);
		Long id2 = getLastCustomerId();
		assertTrue(id1 < id2);
	}

	@Test
	public void checkSelectByNumber() {
		Customer c1 = getLastCustomer();
		Customer c2 = (Customer) (session.createQuery(
		        Customer.findByNumber).setParameter("number",
		        c1.getNumber(), StandardBasicTypes.LONG).uniqueResult());
		assertSame(c1, c2);
	}

	@Test
	// (expected = Exception.class). ???Not working. Hence
	// @ExpectedException(Exception.class) ??try/catch
	// block.
	public void checkThatNumberUnique() {
		try {
			session.persist(c);
			session.persist(new Customer(number1, name1, address1,
			        mobile1));
			session.getTransaction().commit();
			fail("Number must not be duplicate. Ensure index created in database.");
		} catch (Exception ignore) {
		}
	}

	@Test
	public void chgRecord() {
		Customer c = getLastCustomer();
		String newName = c.getName() + "a";
		c.setName(newName);
		Customer c1 = (Customer) session.get(Customer.class, c
		        .getId());
		assertEquals(newName, c1.getName());
	}

	@Test
	public void delRecord() {
		Customer c = getLastCustomer();
		Customer cBeforeDel = getLastCustomer();
		assertSame(c, cBeforeDel);
		session.delete(c);
		Customer cAfterDel = getLastCustomer();
		assertNotSame(c, cAfterDel);
	}

	@Test
	public void ensureNumberNotNull() {
		try {
			Customer c = getLastCustomer();
			c.setNumber(null);
			session.getTransaction().commit();
			fail("Number should have not null constraint in database.");
		} catch (Exception ignore) {
		}
	}

	@Test
	public void ensureNameNotNull() {
		try {
			Customer c = getLastCustomer();
			c.setName(null);
			session.getTransaction().commit();
			fail("Name should have not null constraint in database.");
		} catch (Exception ignore) {
		}
	}

	@Test
	public void ensureAddressNotNull() {
		try {
			Customer c = getLastCustomer();
			c.setAddress(null);
			session.getTransaction().commit();
			fail("Address should have not null constraint in database.");
		} catch (Exception ignore) {
		}
	}

	@Test
	public void ensureMobileNotNull() {
		try {
			Customer c = getLastCustomer();
			c.setMobile(null);
			session.getTransaction().commit();
			fail("Mobile should have not null constraint in database.");
		} catch (Exception ignore) {
		}
	}

	private static final String tableName = "Customer";

	@Test 
	public void checkLengthOfId() throws Exception {
		long length = getMaxLengthOfNumericColumnFromDerby("id",tableName);
		assertTrue(length >= Customer.idLength);
	}

	@Test 
	public void checkLengthOfNumber() throws Exception {
		long length = getMaxLengthOfNumericColumnFromDerby(Customer.numberField,tableName);
		assertTrue(length >= Customer.numberScreenLength);
	}

	@Test 
	public void checkLengthOfName() throws Exception {
		long length = getMaxLengthOfStringColumnFromDerby(Customer.nameField,tableName);
		assertTrue(length >= Customer.nameScreenLength);
	}

	@Test
	public void checkLengthOfAddress() throws Exception {
		long length = getMaxLengthOfStringColumnFromDerby(Customer.addressField,tableName);
		assertTrue(length >= Customer.addressLength);
	}

	@Test
	public void checkLengthOfMobile() throws Exception {
		Long length = getMaxLengthOfStringColumnFromDerby(
		        Customer.mobileField, tableName);
		assertTrue(length >= Customer.mobileScreenLength);
	}
}
