package fr.alib.gotrips.model.entity.user;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

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
	@JoinColumn(name = "flight_company_id", referencedColumnName = "id")
	private FlightCompany flightCompany;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
	@JoinColumn(name = "hotel_company_id", referencedColumnName = "id")
	private HotelCompany hotelCompany;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
	@JoinColumn(name = "activity_company_id", referencedColumnName = "id")
	private ActivityCompany activityCompany;

	@Column(nullable = false)
	private String roles;
	
	@OneToMany( cascade = CascadeType.ALL, orphanRemoval = true )
	@JoinColumn( name = "id" )
	private List<FacturationData> cards = new ArrayList<FacturationData>();

	
}
