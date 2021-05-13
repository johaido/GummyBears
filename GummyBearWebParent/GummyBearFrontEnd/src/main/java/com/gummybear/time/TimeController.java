package com.gummybear.time;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	 * @author Olga
	 */
	@RequestMapping("/checkin")
    public String checkIn(Model model, @AuthenticationPrincipal UserDetails currentUser, RedirectAttributes redirectAttributes) {
		User user = (User) userRepo.getUserByEmail(currentUser.getUsername());
		System.out.println("User ID: " + user.getId());
		System.out.println("User name: " + user.getFirstName());
		TimeStamp ts = service.save(user.getId(), "in");
		redirectAttributes.addFlashAttribute("message", "The Check-In time of " + ts.toString() + " was registered succesfully.");
		return "redirect:/timetracking/";
    }
	
	/**
	 * @author Olga
	 */
	@RequestMapping("/checkout")
    public String checkOut(Model model, @AuthenticationPrincipal UserDetails currentUser, RedirectAttributes redirectAttributes) {
		User user = (User) userRepo.getUserByEmail(currentUser.getUsername());
		System.out.println("User ID: " + user.getId());
		System.out.println("User name: " + user.getFirstName());
		TimeStamp ts = service.save(user.getId(), "out");
		redirectAttributes.addFlashAttribute("message", "The Check-Out time of " + ts.toString() + " was registered succesfully.");
		return "redirect:/timetracking/";
    }
	
	/**
	 * @author Olga
	 */
	@GetMapping("/timetracking")
	public String calculateTimeDifference(Model model, @AuthenticationPrincipal UserDetails currentUser) {
		User user = (User) userRepo.getUserByEmail(currentUser.getUsername());
		System.out.println("User ID: " + user.getId());
		System.out.println("User name: " + user.getFirstName());
		List<TimeDifference> listTimeDifference = service.calculateTimeDifference();
		
		List<TimeDifference> listTimeDifferenceForUser = new ArrayList<>();
		for (TimeDifference td : listTimeDifference) {
			if (td.getUserid() == user.getId()) {
				listTimeDifferenceForUser.add(td);
			}
		}
		
//		model.addAttribute("listTimeDifference", listTimeDifference);
		model.addAttribute("listTimeDifference", listTimeDifferenceForUser);
		return "timetracking";
	}	

	/**
	 * @author Jonas
	 */
	@GetMapping("/timeoverview")
	public String viewTime() {
		//List<Timestatement> listTime = service.listAll();
		//model.addAllAttributes("listTime", listTime);
		return "timeoverview";
	}
		
}
