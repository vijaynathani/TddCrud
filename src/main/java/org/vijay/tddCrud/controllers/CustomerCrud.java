package org.vijay.tddCrud.controllers;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.inject.Provider;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.vijay.tddCrud.domain.CustomerCrudService;
import org.vijay.tddCrud.model.Customer;

@Controller
@RequestMapping("customer")
public class CustomerCrud {
	// This is a singleton. We need a different object for
	// every request. 
	@Inject 
	private Provider<CustomerCrudService> factory;	

	@RequestMapping(method = RequestMethod.GET)
	public String getBlankPage(Model model, HttpSession session) {
		return updateResponse(model, new Customer(), "", session);
	}

	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	public String process(final HttpServletRequest request, final Model model,
			final HttpSession session) {
		CustomerCrudService cv =  factory.get();
		cv.process(request.getParameterMap(),
				(Long) session.getAttribute(getKeyName()));
		return updateResponse(model, cv.getCustomer(), cv.getMessage(), session);
	}

	private String updateResponse(Model model, Customer c, String message,
			HttpSession session) {
		session.setAttribute(getKeyName(), c.getNumber());
		model.addAttribute("customer", c);
		model.addAttribute("message", message);
		return "customer";
	}

	private String getKeyName() {
		return this.getClass().getName();
	}

}
