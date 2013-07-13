package org.vijay.tddCrud.dao;

import org.hibernate.Session;

public class CustomerDbOperateWithSession extends CustomerDbOperate {
	Session s;
	public CustomerDbOperateWithSession(Session s) {
		super(null);
		this.s = s;
	}
	@Override public Session getSession() {
		return s;
	}
}