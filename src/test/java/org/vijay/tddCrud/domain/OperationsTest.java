package org.vijay.tddCrud.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class OperationsTest {

	Map<String, Object> m = new HashMap<String, Object>();
	
	@Test
	public void ensureNoDuplicateDescription() {
		Set<String> descriptions = new HashSet<String>();
		int count = 0;

		for (Operations o : Operations.values()) {
			count++;
			descriptions.add(o.value());
		}
		assertEquals(count, descriptions.size());
	}

	@Test
	public void emptySearch() {
		Operations o = Operations.getOperationInStringKey(m);

		assertSame(Operations.None, o);
	}

	private void addRandomValue() {
		m.put(CustomerCrudServiceTest.getRandomString(100), null);
	}

	@Test
	public void notFoundSearch() {
		addRandomValue();
		Operations o = Operations.getOperationInStringKey(m);

		assertSame(Operations.None, o);
	}

	@Test
	public void searchValidValue() {
		addRandomValue();
		m.put(Operations.Chg.value(), null);
		Operations o = Operations.getOperationInStringKey(m);

		assertSame(Operations.Chg, o);
	}
}
