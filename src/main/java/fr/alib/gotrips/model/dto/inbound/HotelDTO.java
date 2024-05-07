package fr.alib.gotrips.model.dto.inbound;

import java.util.Objects;

import fr.alib.gotrips.model.dto.duplex.AddressDTO;
import fr.alib.gotrips.model.dto.outbound.CompanyDetailsDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class HotelDTO {
	@NotEmpty
	private String name;
	@NotEmpty
	private String description;
	@NotNull
	private Integer rooms;
	private AddressDTO address;
	@NotNull
	private Float pricePerNight;
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
	public Integer getRooms() {
		return rooms;
	}
	public void setRooms(Integer rooms) {
		this.rooms = rooms;
	}
	public AddressDTO getAddress() {
		return address;
	}
	public void setAddress(AddressDTO address) {
		this.address = address;
	}
	public Float getPricePerNight() {
		return pricePerNight;
	}
	public void setPricePerNight(Float pricePerNight) {
		this.pricePerNight = pricePerNight;
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, description, name, pricePerNight, rooms);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HotelDTO other = (HotelDTO) obj;
		return Objects.equals(address, other.address) && Objects.equals(description, other.description)
				&& Objects.equals(name, other.name) && Objects.equals(pricePerNight, other.pricePerNight)
				&& Objects.equals(rooms, other.rooms);
	}
	public HotelDTO(@NotEmpty String name, @NotEmpty String description, @NotNull Integer rooms, AddressDTO address,
			@NotNull Float pricePerNight) {
		super();
		this.name = name;
		this.description = description;
		this.company = company;
		this.rooms = rooms;
		this.address = address;
		this.pricePerNight = pricePerNight;
	}
	public HotelDTO() {
		super();
	}
}
