package fr.alib.gotrips.model.entity.company;

import java.util.ArrayList;
import java.util.List;

import fr.alib.gotrips.model.entity.User;
import fr.alib.gotrips.model.entity.offers.Flight;
import fr.alib.gotrips.model.entity.offers.Hotel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "HOTELCOMPANY")
public class HotelCompany {

	@Id
	private Long id;
	@OneToOne(mappedBy = "USER")
	private User user;
	@Column(nullable = false, unique = true)
	private String name;
	@Column(nullable = true, unique = false)
	private String description;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id")
	private List<Hotel> hotels = new ArrayList<Hotel>();
	
}
