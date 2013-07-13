package org.vijay.tddCrud.common;

import java.lang.reflect.Method;
import static org.junit.Assert.*;

import org.junit.Test;


public class Check {

	public static void EnsureTestMethodPresent(Class<?> c, String methodName) {
		Method m = null;
		try {
			m = c.getMethod(methodName);			
		} catch (Exception e) {
			fail("Test Method absent");
		}
		assertFalse( m.getAnnotation(Test.class) == null);
		assertTrue(m.getReturnType().equals(Void.TYPE));
	}
}
