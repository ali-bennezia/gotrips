package fr.alib.gotrips.model.dto.outbound;

import java.util.List;
import java.util.Objects;

public class AuthenticationSessionDTO {

	private String jwtToken;
	private String username;
	private String id;
	private String email;
	private List<String> roles;
	private CompanyDetailsDTO flightCompany;
	private CompanyDetailsDTO hotelCompany;
	private CompanyDetailsDTO activityCompany;
	
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
	public CompanyDetailsDTO getFlightCompany() {
		return flightCompany;
	}
	public void setFlightCompany(CompanyDetailsDTO flightCompany) {
		this.flightCompany = flightCompany;
	}
	public CompanyDetailsDTO getHotelCompany() {
		return hotelCompany;
	}
	public void setHotelCompany(CompanyDetailsDTO hotelCompany) {
		this.hotelCompany = hotelCompany;
	}
	public CompanyDetailsDTO getActivityCompany() {
		return activityCompany;
	}
	public void setActivityCompany(CompanyDetailsDTO activityCompany) {
		this.activityCompany = activityCompany;
	}
	public AuthenticationSessionDTO(String jwtToken, String username, String id, String email, List<String> roles,
			CompanyDetailsDTO flightCompany, CompanyDetailsDTO hotelCompany, CompanyDetailsDTO activityCompany) {
		super();
		this.jwtToken = jwtToken;
		this.username = username;
		this.id = id;
		this.email = email;
		this.roles = roles;
		this.flightCompany = flightCompany;
		this.hotelCompany = hotelCompany;
		this.activityCompany = activityCompany;
	}
	public AuthenticationSessionDTO() {
		super();
	}
}
