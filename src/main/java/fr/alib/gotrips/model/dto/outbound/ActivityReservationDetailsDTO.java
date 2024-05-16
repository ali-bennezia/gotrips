package fr.alib.gotrips.model.dto.outbound;

import java.util.Objects;

import fr.alib.gotrips.model.entity.reservation.ActivityReservation;
import fr.alib.gotrips.utils.TimeUtils;

public class ActivityReservationDetailsDTO {
	private Long id;
	private UserDetailsDTO user;
	private ActivityDetailsDTO activity;
	private Float price;
	private Long paymentTime;
	private Long beginTime;
	private Long endTime;
	private Integer days;
	
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
	public ActivityDetailsDTO getActivity() {
		return activity;
	}
	public void setActivity(ActivityDetailsDTO activity) {
		this.activity = activity;
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
	public Long getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(activity, beginTime, days, endTime, id, paymentTime, price, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActivityReservationDetailsDTO other = (ActivityReservationDetailsDTO) obj;
		return Objects.equals(activity, other.activity) && Objects.equals(beginTime, other.beginTime)
				&& Objects.equals(days, other.days) && Objects.equals(endTime, other.endTime)
				&& Objects.equals(id, other.id) && Objects.equals(paymentTime, other.paymentTime)
				&& Objects.equals(price, other.price) && Objects.equals(user, other.user);
	}
	
	public ActivityReservationDetailsDTO(Long id, UserDetailsDTO user, ActivityDetailsDTO activity, Float price,
			Long paymentTime, Long beginTime, Long endTime, Integer days) {
		super();
		this.id = id;
		this.user = user;
		this.activity = activity;;
		this.price = price;
		this.paymentTime = paymentTime;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.days = days;
	}
	public ActivityReservationDetailsDTO(ActivityReservation res) {
		super();
		this.id = res.getId();
		this.user = new UserDetailsDTO( res.getActivity().getActivityCompany().getUser() );
		this.activity = new ActivityDetailsDTO( res.getActivity() );
		this.paymentTime = res.getPaymentTime().getTime();
		this.beginTime = res.getBeginDate().getTime();
		this.endTime = res.getEndDate().getTime();
		this.days = (int) TimeUtils.totalDaysWithinDates(res.getBeginDate(), res.getEndDate());
		this.price = res.getActivity().getPricePerDay().floatValue() * days;
	}
	public ActivityReservationDetailsDTO() {
		super();
	}
	
}
