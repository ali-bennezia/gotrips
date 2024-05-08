package fr.alib.gotrips.model.dto.outbound;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

import fr.alib.gotrips.model.entity.offers.Hotel;
import fr.alib.gotrips.model.entity.reservation.HotelReservation;

public class HotelReservationDetailsDTO {
	private Long id;
	private UserDetailsDTO user;
	private HotelDetailsDTO hotel;
	private Float price;
	private Long paymentTime;
	private Long beginTime;
	private Long endTime;
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
	public HotelDetailsDTO getHotel() {
		return hotel;
	}
	public void setHotel(HotelDetailsDTO hotel) {
		this.hotel = hotel;
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
	@Override
	public int hashCode() {
		return Objects.hash(beginTime, endTime, hotel, id, paymentTime, price, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HotelReservationDetailsDTO other = (HotelReservationDetailsDTO) obj;
		return Objects.equals(beginTime, other.beginTime) && Objects.equals(endTime, other.endTime)
				&& Objects.equals(hotel, other.hotel) && Objects.equals(id, other.id)
				&& Objects.equals(paymentTime, other.paymentTime) && Objects.equals(price, other.price)
				&& Objects.equals(user, other.user);
	}
	public HotelReservationDetailsDTO(Long id, UserDetailsDTO user, HotelDetailsDTO hotel, Float price,
			Long paymentTime, Long beginTime, Long endTime) {
		super();
		this.id = id;
		this.user = user;
		this.hotel = hotel;
		this.price = price;
		this.paymentTime = paymentTime;
		this.beginTime = beginTime;
		this.endTime = endTime;
	}
	public HotelReservationDetailsDTO(HotelReservation res) {
		super();
		this.id = res.getId();
		this.user = new UserDetailsDTO( res.getHotel().getHotelCompany().getUser() );
		this.hotel = new HotelDetailsDTO( res.getHotel() );
		this.price = res.getHotel().getPricePerNight().floatValue();
		this.paymentTime = res.getPaymentTime().getTime();
		this.beginTime = res.getBeginDate().getTime();
		this.endTime = res.getEndDate().getTime();
	}
	public HotelReservationDetailsDTO() {
		super();
	}
	
}
