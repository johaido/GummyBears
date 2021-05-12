package com.gummybear;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping("")
	public String viewHomepage() {
		return "index";
	}
	
	@GetMapping("/login")
	public String viewLoginPage() {
		return"login";
	}
}
