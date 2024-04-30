package fr.alib.gotrips.model.entity.reservation;

import fr.alib.gotrips.model.entity.User;
import fr.alib.gotrips.model.entity.offers.Flight;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table( name = "FLIGHTRESERVATION" )
public class FlightReservation {

	@Id
	private Long id;
	@OneToOne(mappedBy = "USER")
	private User user;
	@ManyToOne(targetEntity = Flight.class)
	private Flight flight;
	
	
}
