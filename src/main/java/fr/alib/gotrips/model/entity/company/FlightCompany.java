package fr.alib.gotrips.model.entity.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.alib.gotrips.model.dto.inbound.CompanyDTO;
import fr.alib.gotrips.model.entity.Address;
import fr.alib.gotrips.model.entity.offers.Flight;
import fr.alib.gotrips.model.entity.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table( name = "FLIGHTCOMPANY" )
public class FlightCompany {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne(mappedBy = "flightCompany")
	private User user;
	@Column(nullable = false, unique = true)
	private String name;
	@Column(nullable = true, unique = false)
	private String description;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "flight_company_flight_id", referencedColumnName = "id")
	private List<Flight> flights = new ArrayList<Flight>();
	@Embedded
	private Address address;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Flight> getFlights() {
		return flights;
	}
	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, description, flights, id, name, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlightCompany other = (FlightCompany) obj;
		return Objects.equals(address, other.address) && Objects.equals(description, other.description)
				&& Objects.equals(flights, other.flights) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(user, other.user);
	}
	public FlightCompany(Long id, User user, String name, String description, List<Flight> flights, Address address) {
		super();
		this.id = id;
		this.user = user;
		this.name = name;
		this.description = description;
		this.flights = flights;
		this.address = address;
	}
	public FlightCompany(CompanyDTO dto, User user) {
		super();
		this.user = user;
		this.name = dto.getName();
		this.description = dto.getDescription();
		this.address = new Address(dto.getAddress());
	}
	public FlightCompany() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
