package org.vijay.tddCrud.dao;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.vijay.tddCrud.model.Customer;

@Repository
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CustomerDbOperate implements CustomerDbOperations {
	private Customer cust;
	private final SessionFactory sessionFactory;

	@Inject
	public CustomerDbOperate(SessionFactory sf) {
		sessionFactory = sf;
	}
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void addCustomer(Customer c) {
		getSession().save(c);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkCustomerNumber(Customer c) {
		List<Customer> r = getSession().createQuery(Customer.findByNumber)
				.setParameter("number", c.getNumber(), StandardBasicTypes.LONG)
				.list();
		if (r.isEmpty())
			return false;
		cust = r.get(0);
		return true;
	}

	@Override
	public void deleteCustomer() {
		getSession().delete(cust);
	}

	@Override
	public Customer getLastCustomer() {
		return cust;
	}
}
