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
	private Long joinedAtTime;
	private CompanyDetailsDTO flightCompany;
	private CompanyDetailsDTO hotelCompany;
	private CompanyDetailsDTO activityCompany;
	
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
	public Long getJoinedAtTime() {
		return joinedAtTime;
	}
	public void setJoinedAtTime(Long joinedAtTime) {
		this.joinedAtTime = joinedAtTime;
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
	public UserDetailsDTO(Long id, String username, List<String> roles, Long joinedAtTime,
			CompanyDetailsDTO flightCompany, CompanyDetailsDTO hotelCompany, CompanyDetailsDTO activityCompany) {
		super();
		this.id = id;
		this.username = username;
		this.roles = roles;
		this.joinedAtTime = joinedAtTime;
		this.flightCompany = flightCompany;
		this.hotelCompany = hotelCompany;
		this.activityCompany = activityCompany;
	}
	public UserDetailsDTO(User user) {
		super();
		this.id = user.getId();
		this.username = user.getUsername();
		this.roles = Arrays.asList( user.getRoles().split(", ") );
		this.joinedAtTime = user.getCreatedAt().getTime();
		this.flightCompany = user.getFlightCompany() != null ? new CompanyDetailsDTO( user.getFlightCompany() ) : null;
		this.hotelCompany = user.getHotelCompany() != null ? new CompanyDetailsDTO( user.getHotelCompany() ) : null;
		this.activityCompany = user.getActivityCompany() != null ? new CompanyDetailsDTO( user.getActivityCompany() ) : null;
	}
	public UserDetailsDTO(CustomUserDetails userDetails) {
		super();
		this.id = userDetails.getUser().getId();
		this.username = userDetails.getUser().getUsername();
		this.roles = Arrays.asList( userDetails.getUser().getRoles().split(", ") );
		this.joinedAtTime = userDetails.getUser().getCreatedAt().getTime();
		this.flightCompany = userDetails.getUser().getFlightCompany() != null ? new CompanyDetailsDTO( userDetails.getUser().getFlightCompany() ) : null;
		this.hotelCompany = userDetails.getUser().getFlightCompany() != null ? new CompanyDetailsDTO( userDetails.getUser().getHotelCompany() ) : null;
		this.activityCompany = userDetails.getUser().getFlightCompany() != null ? new CompanyDetailsDTO( userDetails.getUser().getActivityCompany() ) : null;
	}
	public UserDetailsDTO() {
		super();
	}
	@Override
	public int hashCode() {
		return Objects.hash(activityCompany, flightCompany, hotelCompany, id, joinedAtTime, roles, username);
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
		return Objects.equals(activityCompany, other.activityCompany)
				&& Objects.equals(flightCompany, other.flightCompany)
				&& Objects.equals(hotelCompany, other.hotelCompany) && Objects.equals(id, other.id)
				&& Objects.equals(joinedAtTime, other.joinedAtTime) && Objects.equals(roles, other.roles)
				&& Objects.equals(username, other.username);
	}
	@Override
	public String toString() {
		return "UserDetailsDTO [id=" + id + ", username=" + username + ", roles=" + roles + ", joinedAtTime="
				+ joinedAtTime + ", flightCompany=" + flightCompany + ", hotelCompany=" + hotelCompany
				+ ", activityCompany=" + activityCompany + ", getId()=" + getId() + ", getUsername()=" + getUsername()
				+ ", getRoles()=" + getRoles() + ", getJoinedAtTime()=" + getJoinedAtTime() + ", getFlightCompany()="
				+ getFlightCompany() + ", getHotelCompany()=" + getHotelCompany() + ", getActivityCompany()="
				+ getActivityCompany() + ", hashCode()=" + hashCode() + ", getClass()=" + getClass() + ", toString()="
				+ super.toString() + "]";
	}
}
