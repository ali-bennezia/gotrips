package fr.alib.gotrips.model.entity.reservation;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

import fr.alib.gotrips.model.entity.PaymentData;
import fr.alib.gotrips.model.entity.offers.Flight;
import fr.alib.gotrips.model.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table( name = "FLIGHTRESERVATION" )
public class FlightReservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(targetEntity = User.class)
	private User user;
	@ManyToOne(targetEntity = Flight.class)
	private Flight flight;
	@Column(nullable = false, precision = 8, scale = 2)
	private BigDecimal price;
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp paymentTime;
	@Embedded
	private PaymentData paymentData;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Timestamp getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Timestamp paymentTime) {
		this.paymentTime = paymentTime;
	}
	public PaymentData getPaymentData() {
		return paymentData;
	}
	public void setPaymentData(PaymentData paymentData) {
		this.paymentData = paymentData;
	}
	@Override
	public int hashCode() {
		return Objects.hash(flight, id, paymentData, paymentTime, price, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlightReservation other = (FlightReservation) obj;
		return Objects.equals(flight, other.flight) && Objects.equals(id, other.id)
				&& Objects.equals(paymentData, other.paymentData) && Objects.equals(paymentTime, other.paymentTime)
				&& Objects.equals(price, other.price) && Objects.equals(user, other.user);
	}
	public FlightReservation(User user, Flight flight, PaymentData paymentData) {
		super();
		this.user = user;
		this.flight = flight;
		this.price = flight.getPrice();
		this.paymentTime = Timestamp.from(Instant.now());
		this.paymentData = paymentData;
	}

	public FlightReservation() {
		super();
	}
	@Override
	public String toString() {
		return "FlightReservation [id=" + id + ", user=" + user + ", flight=" + flight + ", price=" + price
				+ ", paymentTime=" + paymentTime + ", paymentData=" + paymentData + "]";
	}
	
	
	
}
