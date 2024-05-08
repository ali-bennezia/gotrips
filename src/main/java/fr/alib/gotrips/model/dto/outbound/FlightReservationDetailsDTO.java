package fr.alib.gotrips.model.dto.outbound;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import fr.alib.gotrips.model.entity.reservation.FlightReservation;

public class FlightReservationDetailsDTO {
	private Long id;
	private UserDetailsDTO user;
	private FlightDetailsDTO flight;
	private Float price;
	private Long paymentTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserDetailsDTO getUser() {
		return user;
	}
	public void setUser(UserDetailsDTO user) {
		this.user = user;
	}
	public FlightDetailsDTO getFlight() {
		return flight;
	}
	public void setFlight(FlightDetailsDTO flight) {
		this.flight = flight;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Long getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Long paymentTime) {
		this.paymentTime = paymentTime;
	}
	@Override
	public int hashCode() {
		return Objects.hash(flight, id, paymentTime, price, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlightReservationDetailsDTO other = (FlightReservationDetailsDTO) obj;
		return Objects.equals(flight, other.flight) && Objects.equals(id, other.id)
				&& Objects.equals(paymentTime, other.paymentTime) && Objects.equals(price, other.price)
				&& Objects.equals(user, other.user);
	}
	public FlightReservationDetailsDTO(Long id, UserDetailsDTO user, FlightDetailsDTO flight, Float price,
			Timestamp paymentTime) {
		super();
		this.id = id;
		this.user = user;
		this.flight = flight;
		this.price = price;
		this.paymentTime = paymentTime.getTime();
	}
	public FlightReservationDetailsDTO(FlightReservation reservation) {
		super();
		this.id = reservation.getId();
		this.user = new UserDetailsDTO(reservation.getUser());
		this.flight = new FlightDetailsDTO(reservation.getFlight());
		this.price = reservation.getPrice().floatValue();
		this.paymentTime = reservation.getPaymentTime().getTime();
	}
	public FlightReservationDetailsDTO() {
		super();
	}
	
}
