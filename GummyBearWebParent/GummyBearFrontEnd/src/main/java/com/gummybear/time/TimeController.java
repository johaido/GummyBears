package com.gummybear.time;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gummybear.common.entity.TimeDifference;
import com.gummybear.common.entity.TimeStamp;
import com.gummybear.common.entity.User;
import com.gummybear.user.UserRepository;

@Controller
public class TimeController {

	@Autowired
	private TimeService service;
	
	@Autowired
	private UserRepository userRepo;
	
	/**
	 * @author Olga
	 * @return
	 */
	@GetMapping("/index")
	public String home() {
		return "index";
	}
		
	/**
	 * Save check-in time for a user in the DB.
	 * @author Olga
	 */
	@RequestMapping("/checkin")
    public String checkIn(Model model, @AuthenticationPrincipal UserDetails currentUser, RedirectAttributes redirectAttributes) {
		User user = (User) userRepo.getUserByEmail(currentUser.getUsername());
		TimeStamp ts = service.save(user.getId(), "in");
		redirectAttributes.addFlashAttribute("message", "The Check-In time of " + ts.toString() + " was registered succesfully.");
		return "redirect:/timetracking/";
    }
	
	/**
	 * Save check-out time for a user in the DB.
	 * @author Olga
	 */
	@RequestMapping("/checkout")
    public String checkOut(Model model, @AuthenticationPrincipal UserDetails currentUser, RedirectAttributes redirectAttributes) {
		User user = (User) userRepo.getUserByEmail(currentUser.getUsername());
		TimeStamp ts = service.save(user.getId(), "out");
		redirectAttributes.addFlashAttribute("message", "The Check-Out time of " + ts.toString() + " was registered succesfully.");
		return "redirect:/timetracking/";
    }
	
	/**
	 * From the DB:
	 *   - get tables with the time difference calculated based on check-in/-out time for a user. 
	 *   - get the registered and expected working hours.
	 * Pass them to the model.
	 * @author Olga
	 */
	@GetMapping("/timetracking")
	public String calculateTimeDifference(Model model, @AuthenticationPrincipal UserDetails currentUser) {
		// currentUser.getUsername() returns an email address of the logged in user
		// this email address can be used to fetch a User from the DB
		User user = (User) userRepo.getUserByEmail(currentUser.getUsername());
		
		// Get todays date
		Date today = new Date();
				
		// Get table with time difference for each clock-in and clock-out period
		List<TimeDifference> checkInOutTableAll = service.calculateTimeDifference(user.getId());
		List<TimeDifference> checkInOutTableToday = service.calculateTimeDifferenceByDate(user.getId(), today);
		 
		Duration workingTime = service.calculateWorkingTime(user.getId(), today);
		Duration remainingTime = service.calculateDifferenceInWorkingTime(user.getId(), today, user.getWorkingHours());
		
		String workingTimeString = service.DurationToString(workingTime);
		String remainingTimeString = service.DurationToString(remainingTime);		
		
		model.addAttribute("checkInOutTableAll", checkInOutTableAll);
		model.addAttribute("checkInOutTableToday", checkInOutTableToday);
		model.addAttribute("workingTime", workingTimeString);
		model.addAttribute("remainingTime", remainingTimeString);
		
		return "timetracking";
	}	

	// not used
//	/**
//	 * @author Jonas
//	 */
//	@GetMapping("/timeoverview")
//	public String viewTime() {
//		List<Timestatement> listTime = service.listAll();
//		model.addAllAttributes("listTime", listTime);
//		return "timeoverview";
//	}
		
}
