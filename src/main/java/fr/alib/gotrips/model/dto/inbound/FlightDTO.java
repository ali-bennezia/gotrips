package fr.alib.gotrips.model.dto.inbound;

import java.util.Objects;

import fr.alib.gotrips.model.dto.duplex.AddressDTO;
import jakarta.validation.constraints.NotEmpty;

public class FlightDTO {
	private Long departureTime;
	private Long landingTime;
	private AddressDTO departureAddress;
	private AddressDTO arrivalAddress;
	@NotEmpty
	private String departureAirport;
	@NotEmpty
	private String arrivalAirport;
	private float price;
	private int seats;
	
	public Long getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(Long departureTime) {
		this.departureTime = departureTime;
	}
	public Long getLandingTime() {
		return landingTime;
	}
	public void setLandingTime(Long landingTime) {
		this.landingTime = landingTime;
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
		return Objects.hash(arrivalAddress, arrivalAirport, departureAddress, departureAirport, departureTime,
				landingTime, price, seats);
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
				&& Objects.equals(departureTime, other.departureTime) && Objects.equals(landingTime, other.landingTime)
				&& Float.floatToIntBits(price) == Float.floatToIntBits(other.price) && seats == other.seats;
	}
	public FlightDTO(Long departureTime, Long landingTime, AddressDTO departureAddress, AddressDTO arrivalAddress,
			String departureAirport, String arrivalAirport, float price, int seats) {
		super();
		this.departureTime = departureTime;
		this.landingTime = landingTime;
		this.departureAddress = departureAddress;
		this.arrivalAddress = arrivalAddress;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.price = price;
		this.seats = seats;
	}
	public FlightDTO() {
		super();
	}

}
