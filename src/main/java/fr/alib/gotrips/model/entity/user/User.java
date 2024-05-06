package fr.alib.gotrips.model.entity.user;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;

import fr.alib.gotrips.model.dto.inbound.UserRegisterDTO;
import fr.alib.gotrips.model.entity.company.ActivityCompany;
import fr.alib.gotrips.model.entity.company.FlightCompany;
import fr.alib.gotrips.model.entity.company.HotelCompany;
import fr.alib.gotrips.model.entity.offers.Evaluation;
import fr.alib.gotrips.model.entity.reservation.ActivityReservation;
import fr.alib.gotrips.model.entity.reservation.FlightReservation;
import fr.alib.gotrips.model.entity.reservation.HotelReservation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="USER")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false, unique = false)
	private String firstName;
	@Column(nullable = false, unique = false)
	private String lastName;
	@Column(nullable = false)
	private String password;
	@Column
	private boolean enabled;
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp createdAt;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(name = "flight_reservations_id", referencedColumnName = "id")
	private List<FlightReservation> flightReservations = new ArrayList<FlightReservation>();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(name = "hotel_reservations_id", referencedColumnName = "id")
	private List<HotelReservation> hotelReservations = new ArrayList<HotelReservation>();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(name = "activity_reservations_id", referencedColumnName = "id")
	private List<ActivityReservation> activityReservations = new ArrayList<ActivityReservation>();
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
	@JoinColumn(name = "flight_company_id", referencedColumnName = "id", nullable = true)
	private FlightCompany flightCompany;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
	@JoinColumn(name = "hotel_company_id", referencedColumnName = "id", nullable = true)
	private HotelCompany hotelCompany;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
	@JoinColumn(name = "activity_company_id", referencedColumnName = "id", nullable = true)
	private ActivityCompany activityCompany;

	@Column(nullable = false)
	private String roles;
	
	@OneToMany( cascade = CascadeType.ALL, orphanRemoval = true )
	@JoinColumn( name = "facturation_data_id", referencedColumnName = "id" )
	private List<FacturationData> cards = new ArrayList<FacturationData>();
	
	@OneToMany( cascade = CascadeType.ALL, orphanRemoval = true )
	@JoinColumn(name = "evaluation_user_id", referencedColumnName = "id")
	private List<Evaluation> evaluations = new ArrayList<Evaluation>();

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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public List<FlightReservation> getFlightReservations() {
		return flightReservations;
	}

	public void setFlightReservations(List<FlightReservation> flightReservations) {
		this.flightReservations = flightReservations;
	}

	public List<HotelReservation> getHotelReservations() {
		return hotelReservations;
	}

	public void setHotelReservations(List<HotelReservation> hotelReservations) {
		this.hotelReservations = hotelReservations;
	}

	public List<ActivityReservation> getActivityReservations() {
		return activityReservations;
	}

	public void setActivityReservations(List<ActivityReservation> activityReservations) {
		this.activityReservations = activityReservations;
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

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public List<FacturationData> getCards() {
		return cards;
	}

	public void setCards(List<FacturationData> cards) {
		this.cards = cards;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Evaluation> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	@Override
	public int hashCode() {
		return Objects.hash(activityCompany, activityReservations, cards, createdAt, email, enabled, evaluations,
				firstName, flightCompany, flightReservations, hotelCompany, hotelReservations, id, lastName, password,
				roles, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(activityCompany, other.activityCompany)
				&& Objects.equals(activityReservations, other.activityReservations)
				&& Objects.equals(cards, other.cards) && Objects.equals(createdAt, other.createdAt)
				&& Objects.equals(email, other.email) && enabled == other.enabled
				&& Objects.equals(evaluations, other.evaluations) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(flightCompany, other.flightCompany)
				&& Objects.equals(flightReservations, other.flightReservations)
				&& Objects.equals(hotelCompany, other.hotelCompany)
				&& Objects.equals(hotelReservations, other.hotelReservations) && Objects.equals(id, other.id)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
				&& Objects.equals(roles, other.roles) && Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", password=" + password + ", enabled=" + enabled + ", createdAt="
				+ createdAt + ", flightReservations=" + flightReservations + ", hotelReservations=" + hotelReservations
				+ ", activityReservations=" + activityReservations + ", flightCompany=" + flightCompany
				+ ", hotelCompany=" + hotelCompany + ", activityCompany=" + activityCompany + ", roles=" + roles
				+ ", cards=" + cards + ", evaluations=" + evaluations + ", getId()=" + getId() + ", getUsername()="
				+ getUsername() + ", getEmail()=" + getEmail() + ", getPassword()=" + getPassword() + ", isEnabled()="
				+ isEnabled() + ", getCreatedAt()=" + getCreatedAt() + ", getFlightReservations()="
				+ getFlightReservations() + ", getHotelReservations()=" + getHotelReservations()
				+ ", getActivityReservations()=" + getActivityReservations() + ", getFlightCompany()="
				+ getFlightCompany() + ", getHotelCompany()=" + getHotelCompany() + ", getActivityCompany()="
				+ getActivityCompany() + ", getRoles()=" + getRoles() + ", getCards()=" + getCards()
				+ ", getFirstName()=" + getFirstName() + ", getLastName()=" + getLastName() + ", getEvaluations()="
				+ getEvaluations() + ", hashCode()=" + hashCode() + ", getClass()=" + getClass() + ", toString()="
				+ super.toString() + "]";
	}

	public User(Long id, String username, String email, String firstName, String lastName, String password, boolean enabled, Timestamp createdAt,
			List<FlightReservation> flightReservations, List<HotelReservation> hotelReservations,
			List<ActivityReservation> activityReservations, FlightCompany flightCompany, HotelCompany hotelCompany,
			ActivityCompany activityCompany, String roles, List<FacturationData> cards) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.enabled = enabled;
		this.createdAt = createdAt;
		this.flightReservations = flightReservations;
		this.hotelReservations = hotelReservations;
		this.activityReservations = activityReservations;
		this.flightCompany = flightCompany;
		this.hotelCompany = hotelCompany;
		this.activityCompany = activityCompany;
		this.roles = roles;
		this.cards = cards;
	}
	
	public User(UserRegisterDTO dto, PasswordEncoder pwdEncoder, FlightCompany fComp, HotelCompany hComp, ActivityCompany aComp, String roles) {
		super();
		this.username = dto.getUsername();
		this.email = dto.getEmail();
		this.firstName = dto.getFirstName();
		this.lastName = dto.getLastName();
		this.password = pwdEncoder.encode(dto.getPassword());
		this.enabled = true;
		this.createdAt = Timestamp.from(Instant.now());
		this.flightCompany = fComp;
		this.hotelCompany = hComp;
		this.activityCompany = aComp;
		this.roles = roles;
	}

	public User() {
		super();
	}



	
	
}
