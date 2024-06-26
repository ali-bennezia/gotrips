package fr.alib.gotrips.model.entity.reservation;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

import fr.alib.gotrips.model.entity.PaymentData;
import fr.alib.gotrips.model.entity.offers.Activity;
import fr.alib.gotrips.model.entity.user.User;
import fr.alib.gotrips.utils.TimeUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table( name = "ACTIVITYRESERVATION" )
public class ActivityReservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	@Column( precision = 3, scale = 0 )
	private Integer spots;
	@Temporal(TemporalType.DATE)
	private Date beginDate;
	@Temporal(TemporalType.DATE)
	private Date endDate;
	@Column(nullable = false)
	private Integer days;
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
	
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getSpots() {
		return spots;
	}
	public void setSpots(Integer spots) {
		this.spots = spots;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}

	@Override
	public int hashCode() {
		return Objects.hash(activity, beginDate, days, endDate, id, paymentData, paymentTime, price, spots, user);
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
		return Objects.equals(activity, other.activity) && Objects.equals(beginDate, other.beginDate)
				&& Objects.equals(days, other.days) && Objects.equals(endDate, other.endDate)
				&& Objects.equals(id, other.id) && Objects.equals(paymentData, other.paymentData)
				&& Objects.equals(paymentTime, other.paymentTime) && Objects.equals(price, other.price)
				&& Objects.equals(spots, other.spots) && Objects.equals(user, other.user);
	}
	public ActivityReservation(Long id, User user, Activity activity, BigDecimal price, Integer spots, Timestamp paymentTime,
			PaymentData paymentData, Date beginDate, Date endDate, Integer days) {
		super();
		this.id = id;
		this.user = user;
		this.activity = activity;
		this.price = price;
		this.spots = spots;
		this.paymentTime = paymentTime;
		this.paymentData = paymentData;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.days = days;
	}
	public ActivityReservation(User user, Activity activity, PaymentData paymentData, Date beginDate, Date endDate) {
		super();
		this.user = user;
		this.activity = activity;
		this.spots = activity.getSpots();
		this.paymentTime = Timestamp.from(Instant.now());
		this.paymentData = paymentData;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.days = (int) TimeUtils.totalDaysWithinDates(beginDate, endDate);
		this.price = new BigDecimal( activity.getPricePerDay().floatValue() * (float)days );
	}
	public ActivityReservation() {
		super();
	}
	@Override
	public String toString() {
		return "ActivityReservation [id=" + id + ", user=" + user + ", activity=" + activity + ", price=" + price
				+ ", paymentTime=" + paymentTime + ", paymentData=" + paymentData + ", spots=" + spots + ", beginDate="
				+ beginDate + ", endDate=" + endDate + ", days=" + days + ", getId()=" + getId() + ", getUser()="
				+ getUser() + ", getActivity()=" + getActivity() + ", getPrice()=" + getPrice() + ", getPaymentTime()="
				+ getPaymentTime() + ", getPaymentData()=" + getPaymentData() + ", getBeginDate()=" + getBeginDate()
				+ ", getEndDate()=" + getEndDate() + ", getSpots()=" + getSpots() + ", getDays()=" + getDays()
				+ ", hashCode()=" + hashCode() + ", getClass()=" + getClass() + ", toString()=" + super.toString()
				+ "]";
	}

	
	
}
