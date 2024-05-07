package fr.alib.gotrips.model.dto.inbound;

import java.util.Objects;

import fr.alib.gotrips.model.dto.duplex.AddressDTO;

public class ActivityDTO {
	private String title;
	private String description;
	private AddressDTO address;
	private Float pricePerDay;
	private Integer spots;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public Float getPricePerDay() {
		return pricePerDay;
	}
	public void setPricePerDay(Float pricePerDay) {
		this.pricePerDay = pricePerDay;
	}
	public Integer getSpots() {
		return spots;
	}
	public void setSpots(Integer spots) {
		this.spots = spots;
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, description, pricePerDay, spots, title);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActivityDTO other = (ActivityDTO) obj;
		return Objects.equals(address, other.address) && Objects.equals(description, other.description)
				&& Objects.equals(pricePerDay, other.pricePerDay) && Objects.equals(spots, other.spots)
				&& Objects.equals(title, other.title);
	}
	public ActivityDTO(String title, String description, AddressDTO address, Float pricePerDay, Integer spots) {
		super();
		this.title = title;
		this.description = description;
		this.address = address;
		this.pricePerDay = pricePerDay;
		this.spots = spots;
	}
	public ActivityDTO() {
		super();
	}
}
