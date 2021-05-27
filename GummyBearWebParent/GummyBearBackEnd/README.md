# Administrator

This readme file illustrate how Spring MVC can be used in combination with jQuery to implement a web application.
#### Contents:
- [Dependency and plugin](#dependency-and-plugin)
- [Website with Bootstrap](#website-with-bootstrap)
- [Spring MVC Views and Controller](#spring-mvc-views-and-controller)
- [JavaScript and jQuery](#javascript-and-jquery)
- [Server side](#server-side)
- [Functional Requirements](#functional-requirements)
	- The administrator login
	- The administrator logout
	- The administrator create employee account

```XML
  		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
		</dependency>
```
```XML
		<plugin>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-maven-plugin</artifactId>
		</plugin>
```

## Static Website with Bootstrap

As a first task, a bootstrap based prototype has been created by using a prototyping application. 

In this case, the prototype application Bootstrap Studio has been used to create a basic user interface design based on an HTML grid, Bootstrap CSS and JavaScript, including the selection of web fonts and font-based icons.
```XML
	<link rel="stylesheet" type="text/css" th:href="@{/webjars/bootstrap/css/bootstrap.min.css}"/>
	<link rel="stylesheet" type="text/css" th:href="@{/fontawesome/all.css}"/>
    	<link rel="stylesheet" type="text/css" th:href="@{/style.css}"/>
```
## Spring MVC Views and Controller

The generated static views can be placed under `src\main\resources\static` and `src\main\resources\templates` to make them available. Besides, a plain Spring MVC controller can be implemented as follows to serve the views using a specific path. <br>
> You may change the root and password in `\src\main\resources\application.properties` to access your local database:

## JavaScript and jQuery in users.html
```JavaScript
<!-- Logout/Jonas -->	
<script type="text/javascript">
	$(document).ready(function() {
		$("#logoutLink").on("click", function(e) {
			e.preventDefault();
			document.logoutForm.submit();
		});
	});
</script>

<!-- Delete confirmation/Thitari-->
<script type = "text/javascript">
	$(document).ready(function() {
			$(".link-delete").on("click", function(e) {
				e.preventDefault();
				link = $(this);
				//alert($(this).attr("href"));
				userId = link.attr("userId");
				$("#yesButton").attr("href", link.attr("href"));
				$("#confirmText").text("Are you sure to delete this user id " + userId +"?");
				$("#confirmModal").modal();
				//showDeleteConfirmModal($(this), 'user');
			});
		});
</script>
```

## JavaScript and jQuery in user_form.html
```JavaScript
<!--Email validation/Thitari-->
<script type="text/javascript">
	$(document).ready(function() {
		$("#buttonCancel").on("click", function() {
			window.location = "[[@{/users}]]";
		});
	});
	
	function checkEmailUnique(form){
		//alert('check email unique');
		url = "[[@{/users/check_email}]]";
		userEmail = $("#email").val();
		userId =  $('#id').val();
		csrfValue = $("input[name='_csrf']").val();
		params = {id: userId, email: userEmail, _csrf: csrfValue};

		$.post(url, params, function(response) {
			if (response == "OK") {
				form.submit();
			} else if (response == "Duplicated") {
				showModalDialog("Warning", "There is another user having the email " + userEmail);
			} else {
				showModalDialog("Error", "Unknown response from server");
			}
		}).fail(function() {
			showModalDialog("Error", "Could not connect to the server");
		});

		return false;
	}
	
	function showModalDialog(title, message) {
		$("#modalTitle").text(title);
		$("#modalBody").text(message);
		$("#modalDialog").modal();
	}
</script>
```

## Server side
Application logic and data access to MySQL was implemented as server side Java endpoints. We used JPA and Hibernate – a Java persistence architecture to map and interact with MySQL using Java JDBC drivers

## Functional - Requirements
✔ There is a pre-defined administrator account<br>
✔ The administrator login<br> 
```Java
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new GummyBearUserDetailsService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			 auth.authenticationProvider(authenticationProvider());
	}

	@Override 
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/**").hasAuthority("Admin")
		.anyRequest()
		.authenticated()
		.and().formLogin()
		.loginPage("/login")
		.usernameParameter("email")
		.permitAll()
		.defaultSuccessUrl("/")
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
	}
}
```

✔ The administrator Authentication<br>
```Java
/**
 * @author Jonas
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// ...
	@Bean
	public UserDetailsService userDetailsService() {
		return new GummyBearUserDetailsService();
	}
	
	@Override 
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/**").hasAuthority("Admin")
		.anyRequest()
		.authenticated()
		.and().formLogin()
		.loginPage("/login")
		.usernameParameter("email")
		.permitAll();
	}
	
	// ...
}
```
```Java
@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepo; //update param reop -> userRepo

	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// ...
	/**
	 * @author Thitari
	 * @param id
	 * @throws UserNotFoundException 
	 */
	public User get(Integer id) throws UserNotFoundException {
		try {
		return userRepo.findById(id).get();
		}catch (NoSuchElementException ex) {
			throw new UserNotFoundException("Could not find any user with ID " + id);
		}
	}
	
	// ...
}
```
```Java
/**
 * 
 * @author thitari
 *
 */
public class UserNotFoundException extends Exception {
	public UserNotFoundException(String message) {
		super(message);
	}
}

```

✔ The administrator logout<br> 
```Java
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new GummyBearUserDetailsService();
	}
	
	// ...
	
	/**
	 * @author thitari
	 */
	@Autowired
	private CustomLogoutSuccesHandler logoutSuccesHandler;
	
	@Override 
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/**").hasAuthority("Admin")
		.anyRequest()
		.authenticated()
		.and().formLogin()
		.loginPage("/login")
		.usernameParameter("email")
		.permitAll()
		.defaultSuccessUrl("/")
	}
	
}
```

✔ The server does write log entries to a text file [GummyBearBackEnd.log](https://github.com/johaido/GummyBears/blob/main/GummyBearWebParent/GummyBearBackEnd/GummyBearBackEnd.log)<br>
```Java
@Component
public class CustomLogoutSuccesHandler extends SimpleUrlLogoutSuccessHandler {

	private static final Logger LOG = LoggerFactory.getLogger(CustomLogoutSuccesHandler.class);

	@Autowired
	private UserService service;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		LOG.info("User: " + authentication.getName() + " has logged out.");
		super.onLogoutSuccess(request, response, authentication);
	}
}

```
```Java
public class GummyBearUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GummyBearUserDetailsService.class)

	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		LOGGER.trace("Entering method loadUserbyUsername");
		LOGGER.debug("Authenticating user with email: " +  email);

		User user = userRepo.getUserByEmail(email);
		if(user != null) {
			//LOGGER.warn("We are tesitng logging with Spring Boot");
			LOGGER.info("User: " + email + " logged-in successfully");
			return new GummyBearUserDetails(user);
		}

		LOGGER.error("User not found");
		throw new UsernameNotFoundException("Could not find user with email: " + email);
	}

}
```
application.properties
```XML
logging.file.name=GummyBearBackEnd.log
logging.pattern.file=%d [%thread] %-5level %-50logger{40} : %msg%n
logging.pattern.rolling-file-name=GummyBearBackEnd-%d{yyy-MM-dd}.%i.log
logging.file.max-size=2KB
logging.file.max-history=2
logging.file.total-size-cap=20KB
logging.file.clean-history-on-start=true
```

✔ The administrator can create an account for an employee (Define Name, employment conditions, planned work time, etc.)<br>
```Java
/**
 * Prepares Model to pass it to Views.
 * @author Olga
 *
 */
@Controller
public class UserController {

	@Autowired
	private UserService service;


	/**
	 * List all users stored in the DB.
	 * author Olga
	 * @param model
	 * @return the name of the view to be used to render the model.
	 * 
	 */
	@GetMapping("/users")
	public String listAll(Model model) {
		List<User> listUsers = service.listAll();
		model.addAttribute("listUsers", listUsers);
		return "users";
	}

	/**
	 * @author Thitari
	 * @param model
	 * Retrieved all the objects and put it on model
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
	 * @author thitari
	 * @param id
	 * @param enabled
	 * @param redirectAttributes
	 * @return
	 */
	@GetMapping("/users/{id}/enabled/{status}")
	public String updateUserEnabledStatus(@PathVariable("id") Integer id,
			@PathVariable("status") boolean enabled, 
			RedirectAttributes redirectAttributes) {
		service.updateUserEnableStatus(id, enabled);
		String status = enabled ?  "enabled" : "disabled";
		String message = "The user id " + id  + " has been "+ status;

		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/users";
	} 
}
```

