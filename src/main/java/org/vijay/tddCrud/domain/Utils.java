package org.vijay.tddCrud.domain;

import java.util.Map;

public class Utils {
	private static Utils instance = new Utils();

	private Utils() {
	}

	public static Utils getInstance() {
		return instance;
	}

	public String replaceNullByEmpty(String s) {
		return (s != null) ? s : "";
	}

	public Long replaceNullByZero(Long l) {
		return (l != null) ? l : 0;
	}

	public String findByKey(Map<String, String[]> values, String key) {
		String v = values.get(key)[0];

		return replaceNullByEmpty(v);

	}

	public boolean isValidNumeric(String s) {
		try {
			Long.valueOf(s);
		} catch (Exception c) {
			return false;
		}
		return true;
	}

	public boolean isValidLength(String s, int maxLength) {
		int length = s.length();

		return ((length > 0) && (length <= maxLength));
	}
}
