package org.vijay.tddCrud.common;

import java.io.IOException;

import org.concordion.api.ResultSummary;
import org.concordion.internal.ConcordionBuilder;

public class ConcordionHelper {
	public static void run(Object test) {
		try {
			ResultSummary resultSummary = new ConcordionBuilder()
			        .build().process(test);
			resultSummary.print(System.out, test);
			resultSummary.assertIsSatisfied(test);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
