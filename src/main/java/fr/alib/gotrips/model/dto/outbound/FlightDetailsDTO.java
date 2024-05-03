package fr.alib.gotrips.model.dto.outbound;

import java.util.Date;
import java.util.Objects;

import fr.alib.gotrips.model.entity.offers.Flight;

public class FlightDetailsDTO {
	private Long id;
	private CompanyDetailsDTO company;
	private float averageEvaluation;
	private Date departureDate;
	private Date landingDate;
	private String departureAirport;
	private String arrivalAirport;
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
	@Override
	public int hashCode() {
		return Objects.hash(arrivalAirport, averageEvaluation, company, departureAirport, departureDate, id,
				landingDate);
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
		return Objects.equals(arrivalAirport, other.arrivalAirport)
				&& Float.floatToIntBits(averageEvaluation) == Float.floatToIntBits(other.averageEvaluation)
				&& Objects.equals(company, other.company) && Objects.equals(departureAirport, other.departureAirport)
				&& Objects.equals(departureDate, other.departureDate) && Objects.equals(id, other.id)
				&& Objects.equals(landingDate, other.landingDate);
	}
	public FlightDetailsDTO(Long id, CompanyDetailsDTO company, float averageEvaluation, Date departureDate,
			Date landingDate, String departureAirport, String arrivalAirport) {
		super();
		this.id = id;
		this.company = company;
		this.averageEvaluation = averageEvaluation;
		this.departureDate = departureDate;
		this.landingDate = landingDate;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
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
	}
	
}
