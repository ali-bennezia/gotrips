package fr.alib.gotrips.model.dto.outbound;

import java.util.Objects;

import fr.alib.gotrips.model.dto.duplex.AddressDTO;
import fr.alib.gotrips.model.entity.offers.Activity;

public class ActivityDetailsDTO {
	private Long id;
	private CompanyDetailsDTO company;
	private String title;
	private String description;
	private float averageEvaluation;
	private AddressDTO address;
	private float pricePerDay;
	private int spots;
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
	public float getAverageEvaluation() {
		return averageEvaluation;
	}
	public void setAverageEvaluation(float averageEvaluation) {
		this.averageEvaluation = averageEvaluation;
	}
	public AddressDTO getAddress() {
		return address;
	}
	public void setAddress(AddressDTO address) {
		this.address = address;
	}
	public float getPricePerDay() {
		return pricePerDay;
	}
	public void setPricePerDay(float pricePerDay) {
		this.pricePerDay = pricePerDay;
	}
	public int getSpots() {
		return spots;
	}
	public void setSpots(int spots) {
		this.spots = spots;
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, averageEvaluation, company, description, id, pricePerDay, spots, title);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActivityDetailsDTO other = (ActivityDetailsDTO) obj;
		return Objects.equals(address, other.address)
				&& Float.floatToIntBits(averageEvaluation) == Float.floatToIntBits(other.averageEvaluation)
				&& Objects.equals(company, other.company) && Objects.equals(description, other.description)
				&& Objects.equals(id, other.id)
				&& Float.floatToIntBits(pricePerDay) == Float.floatToIntBits(other.pricePerDay) && spots == other.spots
				&& Objects.equals(title, other.title);
	}
	public ActivityDetailsDTO(Long id, CompanyDetailsDTO company, String title, String description,
			float averageEvaluation, AddressDTO address, float pricePerDay, int spots) {
		super();
		this.id = id;
		this.company = company;
		this.title = title;
		this.description = description;
		this.averageEvaluation = averageEvaluation;
		this.address = address;
		this.pricePerDay = pricePerDay;
		this.spots = spots;
	}
	public ActivityDetailsDTO(Activity activity) {
		super();
		this.id = activity.getId();
		this.company = new CompanyDetailsDTO(activity.getActivityCompany());
		this.title = activity.getTitle();
		this.description = activity.getDescription();
		this.averageEvaluation = activity.getAverageEvaluation().floatValue();
		this.address = new AddressDTO( activity.getAddress() );
		this.pricePerDay = activity.getPricePerDay().floatValue();
		this.spots = activity.getSpots();
	}
	public ActivityDetailsDTO() {
		super();
	}
	
}
