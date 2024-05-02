package fr.alib.gotrips.model.entity.offers;



import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.alib.gotrips.model.entity.Address;
import fr.alib.gotrips.model.entity.company.FlightCompany;
import fr.alib.gotrips.model.entity.reservation.FlightReservation;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "FLIGHT")
public class Flight {

	@Id
	private Long id;
	@ManyToOne( targetEntity = FlightCompany.class )
	private FlightCompany flightCompany;
	
	@OneToMany(orphanRemoval = false)
	@JoinColumn(name = "flight_reservations_id", referencedColumnName = "id")
	private List<FlightReservation> flightReservations = new ArrayList<FlightReservation>();
	
	@OneToMany(orphanRemoval = true)
	@JoinColumn(name = "flight_evaluations_id", referencedColumnName = "id")
	private List<Evaluation> evaluations = new ArrayList<Evaluation>();
	
	@Temporal( TemporalType.TIMESTAMP )
	private Timestamp departureDate;
	@Temporal( TemporalType.TIMESTAMP )
	private Timestamp landingDate;
	
	@Embedded
	private Address departureAddress;
	@Embedded
	private Address arrivalAddress;
	
	@Column( precision = 12, scale = 2 )
	private BigDecimal price;
	@Column( precision = 3, scale = 0 )
	private Integer seats;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public FlightCompany getFlightCompany() {
		return flightCompany;
	}
	public void setFlightCompany(FlightCompany flightCompany) {
		this.flightCompany = flightCompany;
	}
	public List<FlightReservation> getFlightReservations() {
		return flightReservations;
	}
	public void setFlightReservations(List<FlightReservation> flightReservations) {
		this.flightReservations = flightReservations;
	}
	public List<Evaluation> getEvaluations() {
		return evaluations;
	}
	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}
	public Timestamp getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Timestamp departureDate) {
		this.departureDate = departureDate;
	}
	public Timestamp getLandingDate() {
		return landingDate;
	}
	public void setLandingDate(Timestamp landingDate) {
		this.landingDate = landingDate;
	}
	public Address getDepartureAddress() {
		return departureAddress;
	}
	public void setDepartureAddress(Address departureAddress) {
		this.departureAddress = departureAddress;
	}
	public Address getArrivalAddress() {
		return arrivalAddress;
	}
	public void setArrivalAddress(Address arrivalAddress) {
		this.arrivalAddress = arrivalAddress;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getSeats() {
		return seats;
	}
	public void setSeats(Integer seats) {
		this.seats = seats;
	}
	@Override
	public int hashCode() {
		return Objects.hash(arrivalAddress, departureAddress, departureDate, evaluations, flightCompany,
				flightReservations, id, landingDate, price, seats);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flight other = (Flight) obj;
		return Objects.equals(arrivalAddress, other.arrivalAddress)
				&& Objects.equals(departureAddress, other.departureAddress)
				&& Objects.equals(departureDate, other.departureDate) && Objects.equals(evaluations, other.evaluations)
				&& Objects.equals(flightCompany, other.flightCompany)
				&& Objects.equals(flightReservations, other.flightReservations) && Objects.equals(id, other.id)
				&& Objects.equals(landingDate, other.landingDate) && Objects.equals(price, other.price)
				&& Objects.equals(seats, other.seats);
	}
	public Flight(Long id, FlightCompany flightCompany, List<FlightReservation> flightReservations,
			List<Evaluation> evaluations, Timestamp departureDate, Timestamp landingDate, Address departureAddress,
			Address arrivalAddress, BigDecimal price, Integer seats) {
		super();
		this.id = id;
		this.flightCompany = flightCompany;
		this.flightReservations = flightReservations;
		this.evaluations = evaluations;
		this.departureDate = departureDate;
		this.landingDate = landingDate;
		this.departureAddress = departureAddress;
		this.arrivalAddress = arrivalAddress;
		this.price = price;
		this.seats = seats;
	}
	public Flight() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
