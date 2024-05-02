package fr.alib.gotrips.model.entity.reservation;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

import fr.alib.gotrips.model.entity.PaymentData;
import fr.alib.gotrips.model.entity.offers.Activity;
import fr.alib.gotrips.model.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table( name = "ACTIVITYRESERVATION" )
public class ActivityReservation {

	@Id
	private Long id;
	@ManyToOne(targetEntity = User.class)
	private User user;
	@ManyToOne(targetEntity = Activity.class)
	private Activity activity;
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
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
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
		return Objects.hash(activity, id, paymentData, paymentTime, price, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActivityReservation other = (ActivityReservation) obj;
		return Objects.equals(activity, other.activity) && Objects.equals(id, other.id)
				&& Objects.equals(paymentData, other.paymentData) && Objects.equals(paymentTime, other.paymentTime)
				&& Objects.equals(price, other.price) && Objects.equals(user, other.user);
	}
	public ActivityReservation(Long id, User user, Activity activity, BigDecimal price, Timestamp paymentTime,
			PaymentData paymentData) {
		super();
		this.id = id;
		this.user = user;
		this.activity = activity;
		this.price = price;
		this.paymentTime = paymentTime;
		this.paymentData = paymentData;
	}
	public ActivityReservation() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ActivityReservation [id=" + id + ", user=" + user + ", activity=" + activity + ", price=" + price
				+ ", paymentTime=" + paymentTime + ", paymentData=" + paymentData + "]";
	}
	
	
	
}
