package org.vijay.tddCrud.common;

import java.math.BigInteger;
import java.sql.ResultSet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

abstract public class BaseTest {
	@Autowired
	protected SessionFactory sessionFactory;
	public Session session;

	@Before
	public void createSession() {
		session = sessionFactory.openSession();
		session.beginTransaction();
	}

	@After
	public void closeSession() {
		session.getTransaction().rollback();
		session.close();
		//Spring has @Transactional and @TransactionConfiguration(defaultRollback = true)
        //but these work only for simple cases.
		//For tests that issue commits and start new transactions, these annotations give error in Spring 3.2.
		//Hence the equivalent functionality has been coded here.
	}

	public static final String currentSchema = "test";

	private Long getColumnLengthForMysql(boolean isNumeric, String column,
	        String table) {
		String alphaOrNumeric = isNumeric ? "numeric_precision"
		        : "character_maximum_length";
		String sql = "SELECT " + alphaOrNumeric
		        + " FROM Information_schema.COLUMNS "
		        + "where table_name='" + table
		        + "' and table_Schema='" + currentSchema
		        + "' and column_name='" + column + "';";
		BigInteger b = (BigInteger) session.createSQLQuery(sql)
		        .uniqueResult();
		return b.longValue();
	}

	protected Long getMaxLengthOfNumericColumnFromMysql(
	        String column, String table) {
		return getColumnLengthForMysql(true, column, table);
	}

	protected Long getMaxLengthOfStringColumnFromMysql(
	        String column, String table) {
		return getColumnLengthForMysql(false, column, table);
	}
	private Long actualColumnLengthPossibleInDigitsForDerby(Long receivedColumnLength) {
		final int subtractDigitsSinceLastDigitMayNotBe9 = 1;
		return receivedColumnLength - subtractDigitsSinceLastDigitMayNotBe9;
	}
	private Long getLengthOfColumnForDerby(String column, String table, String expectedType) throws Exception {
		ResultSet c = ((SessionImpl)session).connection().getMetaData().getColumns(
        		null, null, table.toUpperCase(), column.toUpperCase());
		long size = 0;
        try {
        	c.next();
            if (c.getString("TYPE_NAME").equalsIgnoreCase(expectedType))
            	size = c.getInt("COLUMN_SIZE");
        } finally {
        	c.close();
        }
        return size;
	}
	protected Long getMaxLengthOfNumericColumnFromDerby(
	        String column, String table) throws Exception {
        return actualColumnLengthPossibleInDigitsForDerby(getLengthOfColumnForDerby(column, table, "Integer")); 
	}
	protected Long getMaxLengthOfStringColumnFromDerby(
	        String column, String table) throws Exception {
        return getLengthOfColumnForDerby(column, table, "varchar"); 
	}

	public void commitTransactionAndBeginNewTransaction() {
		session.getTransaction().commit();
		session.beginTransaction();
	}
}
