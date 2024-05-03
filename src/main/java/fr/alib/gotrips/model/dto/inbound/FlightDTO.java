package fr.alib.gotrips.model.dto.inbound;

import java.util.Date;
import java.util.Objects;

public class FlightDTO {
	private Date departureDate;
	private Date landingDate;
	private AddressDTO departureAddress;
	private AddressDTO arrivalAddress;
	private String departureAirport;
	private String arrivalAirport;
	private float price;
	private int seats;
	
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	public Date getLandingDate() {
		return landingDate;
	}
	public void setLandingDate(Date landingDate) {
		this.landingDate = landingDate;
	}
	public AddressDTO getDepartureAddress() {
		return departureAddress;
	}
	public void setDepartureAddress(AddressDTO departureAddress) {
		this.departureAddress = departureAddress;
	}
	public AddressDTO getArrivalAddress() {
		return arrivalAddress;
	}
	public void setArrivalAddress(AddressDTO arrivalAddress) {
		this.arrivalAddress = arrivalAddress;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getSeats() {
		return seats;
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}
	
	public String getDepartureAirport() {
		return departureAirport;
	}
	public void setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
	}
	public String getArrivalAirport() {
		return arrivalAirport;
	}
	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}
	@Override
	public int hashCode() {
		return Objects.hash(arrivalAddress, arrivalAirport, departureAddress, departureAirport, departureDate,
				landingDate, price, seats);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlightDTO other = (FlightDTO) obj;
		return Objects.equals(arrivalAddress, other.arrivalAddress)
				&& Objects.equals(arrivalAirport, other.arrivalAirport)
				&& Objects.equals(departureAddress, other.departureAddress)
				&& Objects.equals(departureAirport, other.departureAirport)
				&& Objects.equals(departureDate, other.departureDate) && Objects.equals(landingDate, other.landingDate)
				&& Float.floatToIntBits(price) == Float.floatToIntBits(other.price) && seats == other.seats;
	}
	public FlightDTO(Date departureDate, Date landingDate, AddressDTO departureAddress, AddressDTO arrivalAddress,
			String departureAirport, String arrivalAirport, float price, int seats) {
		super();
		this.departureDate = departureDate;
		this.landingDate = landingDate;
		this.departureAddress = departureAddress;
		this.arrivalAddress = arrivalAddress;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.price = price;
		this.seats = seats;
	}
	public FlightDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
