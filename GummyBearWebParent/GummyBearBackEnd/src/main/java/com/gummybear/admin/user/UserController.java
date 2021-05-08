package com.gummybear.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gummybear.common.entity.Role;
import com.gummybear.common.entity.User;


@Controller
public class UserController {

	@Autowired
	private UserService service;
	
	
	@GetMapping("/users")
	public String listAll(Model model) {
		List<User> listUsers = service.listAll();
		model.addAttribute("listUsers", listUsers);

		return "users";
	}
	
	
	
	/**
    * @author Thitari
    * @param model
    * @return
    * retrived all the objects and put it on model
    */
   @GetMapping("/users/new")
   public String newUser(Model model) {
       List <Role> listRoles = service.listRoles();
       User user = new User();
       user.setEnabled(true);

       model.addAttribute("user", user);
       model.addAttribute("listRoles", listRoles);
       model.addAttribute("pageTitile", "Create New User");
       return "user_form";
   }
   
   /**
    * @author Thitari
    * Use @PostMapping to handling HTTP Post request
    */
  @PostMapping("/users/save")
  public String saveUser(User user, RedirectAttributes redirectAttributes) {
      System.out.println(user);
      service.save(user);

      redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");
      return "redirect:/users";
  }

  /**
   * @author Thitari
   */
  @GetMapping("/users/edit/{id}")
  public String editUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
      try {
          User user = service.get(id);
          List <Role> listRoles = service.listRoles();
          
          model.addAttribute("user", user);
          model.addAttribute("pageTitile", "Edit User (ID: " + id + ")");
          model.addAttribute("listRoles", listRoles);
          return "user_form";

      }
      catch (UserNotFoundException ex) {
          redirectAttributes.addFlashAttribute("message", ex.getMessage());
          return "redirect:/users";
      }
  }
  
  /**
   * @author Thitari
   */
  @GetMapping("/users/delete/{id}")
  public String deleteUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
      try {
          service.delete(id);
          redirectAttributes.addFlashAttribute("message", "The user ID " + id + " has been deleted. successfully");
      }
      catch (UserNotFoundException ex) {
          redirectAttributes.addFlashAttribute("message", ex.getMessage());
      }
      return "redirect:/users";
  }
  
  
  /**
   * @author Thtiari
   * To match in the url listing (users.html)page
   */
  @GetMapping("/users/{id}/enabled/{status}")
  public String updateUserEnabledStatus(@PathVariable("id") Integer id,
		  @PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
	  service.updateUserEnabledStatus(id, enabled);
	  String status = enabled ? "enabled" : "disabled";
	  String message = "The user ID " + id + " has been " + status;
	  redirectAttributes.addFlashAttribute("message", message);
	  return "redirect:/users";
  }
}
