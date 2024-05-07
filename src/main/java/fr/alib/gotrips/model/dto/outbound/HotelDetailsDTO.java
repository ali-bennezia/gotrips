package fr.alib.gotrips.model.dto.outbound;

import java.util.Objects;

import fr.alib.gotrips.model.dto.duplex.AddressDTO;
import fr.alib.gotrips.model.entity.offers.Hotel;

public class HotelDetailsDTO {
	private Long id;
	private CompanyDetailsDTO company;
	private int rooms;
	private String name;
	private String description;
	private AddressDTO address;
	private float averageEvaluation;
	private float pricePerNight;
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
	public int getRooms() {
		return rooms;
	}
	public void setRooms(int rooms) {
		this.rooms = rooms;
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
	public AddressDTO getAddress() {
		return address;
	}
	public void setAddress(AddressDTO address) {
		this.address = address;
	}
	public float getAverageEvaluation() {
		return averageEvaluation;
	}
	public void setAverageEvaluation(float averageEvaluation) {
		this.averageEvaluation = averageEvaluation;
	}
	public float getPricePerNight() {
		return pricePerNight;
	}
	public void setPricePerNight(float pricePerNight) {
		this.pricePerNight = pricePerNight;
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, averageEvaluation, company, description, id, name, pricePerNight, rooms);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HotelDetailsDTO other = (HotelDetailsDTO) obj;
		return Objects.equals(address, other.address)
				&& Float.floatToIntBits(averageEvaluation) == Float.floatToIntBits(other.averageEvaluation)
				&& Objects.equals(company, other.company) && Objects.equals(description, other.description)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Float.floatToIntBits(pricePerNight) == Float.floatToIntBits(other.pricePerNight)
				&& rooms == other.rooms;
	}
	public HotelDetailsDTO(Long id, CompanyDetailsDTO company, int rooms, String name, String description, AddressDTO address, float averageEvaluation,
			float pricePerNight) {
		super();
		this.id = id;
		this.company = company;
		this.rooms = rooms;
		this.name = name;
		this.description = description;
		this.address = address;
		this.averageEvaluation = averageEvaluation;
		this.pricePerNight = pricePerNight;
	}
	public HotelDetailsDTO(Hotel hotel) {
		super();
		this.id = hotel.getId();
		this.company = new CompanyDetailsDTO(hotel.getHotelCompany());
		this.rooms = hotel.getRooms();
		this.name = hotel.getName();
		this.description = hotel.getDescription();
		this.address = new AddressDTO(hotel.getAddress());
		this.averageEvaluation = hotel.getAverageEvaluation().floatValue();
		this.pricePerNight = hotel.getPricePerNight().floatValue();
	}
	public HotelDetailsDTO() {
		super();
	}
}
