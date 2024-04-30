package fr.alib.gotrips.model.entity.company.flight;


import java.util.Date;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Flight {

	@Id
	private Long id;
	
	@ManyToOne
	@JoinColumn( name = "id" )
	private FlightCompany flightCompany;
	
	private Date departureDate;
	private Date landingDate;
	
}
