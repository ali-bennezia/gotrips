package fr.alib.gotrips.model.entity.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.alib.gotrips.model.entity.Address;
import fr.alib.gotrips.model.entity.offers.Flight;
import fr.alib.gotrips.model.entity.offers.Hotel;
import fr.alib.gotrips.model.entity.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
	@OneToOne(mappedBy = "hotelCompany")
	private User user;
	@Column(nullable = false, unique = true)
	private String name;
	@Column(nullable = true, unique = false)
	private String description;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id")
	private List<Hotel> hotels = new ArrayList<Hotel>();
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
	public List<Hotel> getHotels() {
		return hotels;
	}
	public void setHotels(List<Hotel> hotels) {
		this.hotels = hotels;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, description, hotels, id, name, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HotelCompany other = (HotelCompany) obj;
		return Objects.equals(address, other.address) && Objects.equals(description, other.description)
				&& Objects.equals(hotels, other.hotels) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(user, other.user);
	}
	public HotelCompany(Long id, User user, String name, String description, List<Hotel> hotels, Address address) {
		super();
		this.id = id;
		this.user = user;
		this.name = name;
		this.description = description;
		this.hotels = hotels;
		this.address = address;
	}
	public HotelCompany() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
