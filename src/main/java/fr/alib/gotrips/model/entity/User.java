package fr.alib.gotrips.model.entity;

import fr.alib.gotrips.model.entity.company.ActivityCompany;
import fr.alib.gotrips.model.entity.company.HotelCompany;
import fr.alib.gotrips.model.entity.company.flight.FlightCompany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="USER")
public class User {
	
	@Id
	private Long id;
	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String password;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "flight_company_id", referencedColumnName = "id", nullable = true)
	private FlightCompany flightCompany;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "hotel_company_id", referencedColumnName = "id", nullable = true)
	private HotelCompany hotelCompany;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "activity_company_id", referencedColumnName = "id", nullable = true)
	private ActivityCompany activityCompany;

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

	public FlightCompany getFlightCompany() {
		return flightCompany;
	}

	public void setFlightCompany(FlightCompany flightCompany) {
		this.flightCompany = flightCompany;
	}

	public HotelCompany getHotelCompany() {
		return hotelCompany;
	}

	public void setHotelCompany(HotelCompany hotelCompany) {
		this.hotelCompany = hotelCompany;
	}

	public ActivityCompany getActivityCompany() {
		return activityCompany;
	}

	public void setActivityCompany(ActivityCompany activityCompany) {
		this.activityCompany = activityCompany;
	}

	public User(Long id, String username, String email, String password, FlightCompany flightCompany,
			HotelCompany hotelCompany, ActivityCompany activityCompany) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.flightCompany = flightCompany;
		this.hotelCompany = hotelCompany;
		this.activityCompany = activityCompany;
	}

	public User() {
		super();
	}

	
	
}
