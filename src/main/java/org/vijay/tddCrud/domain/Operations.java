package org.vijay.tddCrud.domain;

import java.util.Map;

public enum Operations {
	Add("Add"), Chg("Chg"), View("View"), Del("Del"), None("none");
	Operations(String description) {
		this.description = description;
	}

	private final String description;

	public String value() {
		return description;
	}

	public static Operations getOperationInStringKey(
			Map<String, ? extends Object> m) {
		for (Operations o : Operations.values())
			if (m.containsKey(o.value()))
				return o;
		return None;
	}

}
