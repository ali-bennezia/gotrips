package fr.alib.gotrips.model.entity.offers;



import java.math.BigDecimal;
import java.sql.Timestamp;

import fr.alib.gotrips.model.entity.Address;
import fr.alib.gotrips.model.entity.company.FlightCompany;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "FLIGHT")
public class Flight {

	@Id
	private Long id;
	@ManyToOne( targetEntity = FlightCompany.class )
	private FlightCompany flightCompany;
	@Temporal( TemporalType.TIMESTAMP )
	private Timestamp departureDate;
	@Temporal( TemporalType.TIMESTAMP )
	private Timestamp landingDate;
	@Embedded
	private Address departureAddress;
	@Embedded
	private Address arrivalAddress;
	@Column( precision = 12, scale = 2 )
	private BigDecimal price;
	@Column( precision = 3, scale = 0 )
	private Integer seats;
	
}
