package fr.alib.gotrips.model.entity.company.flight;

import java.util.ArrayList;
import java.util.List;

import fr.alib.gotrips.model.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table( name = "FLIGHTCOMPANY" )
public class FlightCompany {

	@Id
	private Long id;
	
	@OneToOne(mappedBy = "USER")
	private User user;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@OneToMany( targetEntity = Flight.class, mappedBy = "FLIGHTCOMPANY" )
	private List<Flight> flights = new ArrayList<Flight>();
	
}
