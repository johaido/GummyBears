package com.gummybear.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gummybear.common.entity.User;

@Controller
public class TimeController {

	@Autowired
	private TimeService service;
	
	/**
	 * @author Olga
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping("/checkin")
	public String checkIn(Model model, RedirectAttributes redirectAttributes) {
		service.save(true);
		redirectAttributes.addFlashAttribute("message", "The Check-in time was registered succesfully.");
		return "redirect:/";
	}
	
	/**
	 * 
	 * @author Olga
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping("/checkout")
	public String checkOut(Model model, RedirectAttributes redirectAttributes) {
		service.save(false);
		redirectAttributes.addFlashAttribute("message", "The Check-out time was registered succesfully.");
		return "redirect:/";
	}
	
	
	// Temporarily commented methods below as they were causing the error message
	
//	@GetMapping("/timeoverview")
//	public String listAll(Model model) {
//		List<Time> listTime = service.listAll();
//		model.addAttribute(listTime, listTime);
//		return "timeoverview";
//	}
	
//	@GetMapping("/timetracking")
//	public String timeTracking() {
//		return "timetracking";
//	}
	
	/**
	 * @author Jonas
	 */
	@GetMapping("/timeoverview")
	public String viewTime() {
		//List<Timestatement> listTime = service.listAll();
		//model.addAllAttributes("listTime", listTime);
		return "timeoverview";
	}
	
	@GetMapping("/timetracking")
	public String trackTime() {
		return "timetracking";
	}
	
}
