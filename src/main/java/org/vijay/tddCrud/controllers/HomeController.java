package org.vijay.tddCrud.controllers;

import java.text.DateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	@RequestMapping(value = { "/", "/favicon.ico" })
	public ModelAndView home() {
		ModelAndView model = new ModelAndView("home");
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG);
		String formattedDate = dateFormat.format(date);
		model.addObject("serverTime", formattedDate);
		return model;
	}
}
