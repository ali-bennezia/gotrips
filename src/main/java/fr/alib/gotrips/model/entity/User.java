package fr.alib.gotrips.model.entity;

import java.util.List;
import java.util.Objects;

import fr.alib.gotrips.model.entity.company.ActivityCompany;
import fr.alib.gotrips.model.entity.company.FlightCompany;
import fr.alib.gotrips.model.entity.company.HotelCompany;
import fr.alib.gotrips.model.entity.reservation.ActivityReservation;
import fr.alib.gotrips.model.entity.reservation.FlightReservation;
import fr.alib.gotrips.model.entity.reservation.HotelReservation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
	@Column
	private boolean enabled;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(name = "flight_reservations_id", referencedColumnName = "id")
	private List<FlightReservation> flightReservations;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(name = "hotel_reservations_id", referencedColumnName = "id")
	private List<HotelReservation> hotelReservations;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(name = "activity_reservations_id", referencedColumnName = "id")
	private List<ActivityReservation> activityReservations;
	
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

	
}
