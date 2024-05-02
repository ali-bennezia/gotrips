package fr.alib.gotrips.model.dto;

import java.util.Objects;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserRegisterDTO {

	@NotEmpty
	@Size(min = 6, max = 60)
	private String username;
	@Email
	private String email;
	@NotEmpty
	@Size(min = 8, max = 128)
	private String password;
	
	private CompanyDTO flightCompany, hotelCompany, activityCompany;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public CompanyDTO getFlightCompany() {
		return flightCompany;
	}

	public void setFlightCompany(CompanyDTO flightCompany) {
		this.flightCompany = flightCompany;
	}

	public CompanyDTO getHotelCompany() {
		return hotelCompany;
	}

	public void setHotelCompany(CompanyDTO hotelCompany) {
		this.hotelCompany = hotelCompany;
	}

	public CompanyDTO getActivityCompany() {
		return activityCompany;
	}

	public void setActivityCompany(CompanyDTO activityCompany) {
		this.activityCompany = activityCompany;
	}

	@Override
	public int hashCode() {
		return Objects.hash(activityCompany, email, flightCompany, hotelCompany, password, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRegisterDTO other = (UserRegisterDTO) obj;
		return Objects.equals(activityCompany, other.activityCompany) && Objects.equals(email, other.email)
				&& Objects.equals(flightCompany, other.flightCompany)
				&& Objects.equals(hotelCompany, other.hotelCompany) && Objects.equals(password, other.password)
				&& Objects.equals(username, other.username);
	}

	public UserRegisterDTO(@NotEmpty @Size(min = 6, max = 60) String username, @Email String email,
			@NotEmpty @Size(min = 8, max = 128) String password, CompanyDTO flightCompany, CompanyDTO hotelCompany,
			CompanyDTO activityCompany) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.flightCompany = flightCompany;
		this.hotelCompany = hotelCompany;
		this.activityCompany = activityCompany;
	}

	public UserRegisterDTO() {
		super();
	}

	@Override
	public String toString() {
		return "UserRegisterDTO [username=" + username + ", email=" + email + ", password=" + password
				+ ", flightCompany=" + flightCompany + ", hotelCompany=" + hotelCompany + ", activityCompany="
				+ activityCompany + "]";
	}
	
	
	
}
