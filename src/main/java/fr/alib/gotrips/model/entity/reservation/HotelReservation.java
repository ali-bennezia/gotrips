package fr.alib.gotrips.model.entity.reservation;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

import fr.alib.gotrips.model.entity.PaymentData;
import fr.alib.gotrips.model.entity.offers.Hotel;
import fr.alib.gotrips.model.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table( name = "HOTELRESERVATION" )
public class HotelReservation {

	@Id
	private Long id;
	@ManyToOne(targetEntity = User.class)
	private User user;
	@ManyToOne(targetEntity = Hotel.class)
	private Hotel hotel;
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
	public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
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
		return Objects.hash(hotel, id, paymentData, paymentTime, price, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HotelReservation other = (HotelReservation) obj;
		return Objects.equals(hotel, other.hotel) && Objects.equals(id, other.id)
				&& Objects.equals(paymentData, other.paymentData) && Objects.equals(paymentTime, other.paymentTime)
				&& Objects.equals(price, other.price) && Objects.equals(user, other.user);
	}
	public HotelReservation(Long id, User user, Hotel hotel, BigDecimal price, Timestamp paymentTime,
			PaymentData paymentData) {
		super();
		this.id = id;
		this.user = user;
		this.hotel = hotel;
		this.price = price;
		this.paymentTime = paymentTime;
		this.paymentData = paymentData;
	}
	public HotelReservation() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "HotelReservation [id=" + id + ", user=" + user + ", hotel=" + hotel + ", price=" + price
				+ ", paymentTime=" + paymentTime + ", paymentData=" + paymentData + "]";
	}
	
	
	
}
