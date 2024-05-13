package fr.alib.gotrips.model.dto.outbound;

import java.util.List;
import java.util.Objects;

public class AuthenticationSessionDTO {

	private String jwtToken;
	private String username;
	private String id;
	private String email;
	private List<String> roles;
	
	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	@Override
	public int hashCode() {
		return Objects.hash(email, id, jwtToken, roles, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthenticationSessionDTO other = (AuthenticationSessionDTO) obj;
		return Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(jwtToken, other.jwtToken) && Objects.equals(roles, other.roles)
				&& Objects.equals(username, other.username);
	}
	public AuthenticationSessionDTO(String jwtToken, String username, String id, String email, List<String> roles) {
		super();
		this.jwtToken = jwtToken;
		this.username = username;
		this.id = id;
		this.email = email;
		this.roles = roles;
	}
	public AuthenticationSessionDTO() {
		super();
	}
	
}
