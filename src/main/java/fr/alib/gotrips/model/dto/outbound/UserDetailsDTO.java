package fr.alib.gotrips.model.dto.outbound;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.userdetails.UserDetails;

import fr.alib.gotrips.model.auth.CustomUserDetails;
import fr.alib.gotrips.model.entity.user.User;

public class UserDetailsDTO {

	private Long id;
	private String username;
	private List<String> roles;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public UserDetailsDTO(Long id, String username, List<String> roles) {
		super();
		this.id = id;
		this.username = username;
		this.roles = roles;
	}
	public UserDetailsDTO(User user) {
		super();
		this.id = user.getId();
		this.username = user.getUsername();
		this.roles = Arrays.asList( user.getRoles().split(", ") );
	}
	public UserDetailsDTO(CustomUserDetails userDetails) {
		super();
		this.id = userDetails.getUser().getId();
		this.username = userDetails.getUser().getUsername();
		this.roles = Arrays.asList( userDetails.getUser().getRoles().split(", ") );
	}
	public UserDetailsDTO() {
		super();
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, roles, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDetailsDTO other = (UserDetailsDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(roles, other.roles)
				&& Objects.equals(username, other.username);
	}
	@Override
	public String toString() {
		return "UserDetailsDTO [id=" + id + ", username=" + username + ", roles=" + roles + "]";
	}
	
	
	
}
