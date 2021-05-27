# Entities
We created entities  in packgage `src\main\java\com\gummybear\common\entity` to connected with gummbeardb in mySQL

- [User](user)
- [Role](role)

## User
There is a user table in mySQL
```Java
/**
 * Class to construct users table in the DB.
 * @author Olga
 */
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; 

	@Column(length = 128, nullable = false, unique = true)
	private String email;

	@Column(length = 64, nullable = false)
	private String password;

	@Column(name = "first_name", length = 45, nullable = false)
	private String firstName;

	@Column(name = "last_name", length = 45, nullable = false)
	private String lastName;

	@Column(name = "job_title", length = 64)
	private String jobTitle;

	@Column(name = "working_hours", nullable = false) private Double
	workingHours;
  
  	private boolean enabled;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
			)
	private Set<Role> roles = new HashSet<>();

	public User() {}

  // ... 
  // Constructor
  // Getters , Setters
}
  ```
  
 ## Role
 There is role table in mySQL
```Java
@Entity
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 40, nullable = false, unique = true)
	private String name;
	
	@Column(length = 150, nullable = false)
	private String description;
	
	// ...
	// constructors
	// getters and setters
	
	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
 ```
 ![image](https://github.com/Thitari-Somboon/GummyBears/blob/main/Image/Table%20role.jpg)
 
 
