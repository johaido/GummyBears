package com.gummybear.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TimeController {

	@Autowired
	private TimeService service;
	
	@GetMapping("/timeoverview")
	public String listAll(Model model) {
		List<Time> listTime = service.listAll();
		model.addAttribute(listTime, listTime);
		return "timeoverview";
	}
	
	@GetMapping("/timetracking")
	public String timeTracking() {
		return "timetracking";
	}
	
	
	
	
}
