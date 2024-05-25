package fr.alib.gotrips.model.dto.outbound;

import java.util.Date;
import java.util.Objects;

import fr.alib.gotrips.model.dto.duplex.AddressDTO;
import fr.alib.gotrips.model.entity.offers.Flight;

public class FlightDetailsDTO {
	private Long id;
	private CompanyDetailsDTO company;
	private float averageEvaluation;
	private Date departureDate;
	private Date landingDate;
	private String departureAirport;
	private String arrivalAirport;
	float price;
	private AddressDTO departureAddress;
	private AddressDTO arrivalAddress;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public CompanyDetailsDTO getCompany() {
		return company;
	}
	public void setCompany(CompanyDetailsDTO company) {
		this.company = company;
	}
	public float getAverageEvaluation() {
		return averageEvaluation;
	}
	public void setAverageEvaluation(float averageEvaluation) {
		this.averageEvaluation = averageEvaluation;
	}
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
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
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

	@Override
	public int hashCode() {
		return Objects.hash(arrivalAddress, arrivalAirport, averageEvaluation, company, departureAddress,
				departureAirport, departureDate, id, landingDate, price);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlightDetailsDTO other = (FlightDetailsDTO) obj;
		return Objects.equals(arrivalAddress, other.arrivalAddress)
				&& Objects.equals(arrivalAirport, other.arrivalAirport)
				&& Float.floatToIntBits(averageEvaluation) == Float.floatToIntBits(other.averageEvaluation)
				&& Objects.equals(company, other.company) && Objects.equals(departureAddress, other.departureAddress)
				&& Objects.equals(departureAirport, other.departureAirport)
				&& Objects.equals(departureDate, other.departureDate) && Objects.equals(id, other.id)
				&& Objects.equals(landingDate, other.landingDate)
				&& Float.floatToIntBits(price) == Float.floatToIntBits(other.price);
	}
	public FlightDetailsDTO(Long id, CompanyDetailsDTO company, float averageEvaluation, Date departureDate,
			Date landingDate, String departureAirport, String arrivalAirport, float price, AddressDTO departureAddress, AddressDTO arrivalAddress) {
		super();
		this.id = id;
		this.company = company;
		this.averageEvaluation = averageEvaluation;
		this.departureDate = departureDate;
		this.landingDate = landingDate;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.price = price;
		this.departureAddress = departureAddress;
		this.arrivalAddress = arrivalAddress;
	}
	public FlightDetailsDTO() {
		super();
	}
	public FlightDetailsDTO(Flight flight) {
		super();
		this.id = flight.getId();
		this.company = new CompanyDetailsDTO(flight.getFlightCompany());
		this.averageEvaluation = flight.getAverageEvaluation().floatValue();
		this.departureDate = flight.getDepartureDate();
		this.landingDate = flight.getLandingDate();
		this.departureAirport = flight.getDepartureAirport();
		this.arrivalAirport = flight.getArrivalAirport();
		this.price = flight.getPrice().floatValue();
		this.departureAddress = new AddressDTO(flight.getDepartureAddress());
		this.arrivalAddress = new AddressDTO(flight.getArrivalAddress());
	}
	
}
