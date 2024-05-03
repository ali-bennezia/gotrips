package fr.alib.gotrips.model.dto.outbound;

import java.util.Objects;

import fr.alib.gotrips.model.entity.company.ActivityCompany;
import fr.alib.gotrips.model.entity.company.FlightCompany;
import fr.alib.gotrips.model.entity.company.HotelCompany;

public class CompanyDetailsDTO {
	private Long id;
	private String name;
	private String description;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	@Override
	public int hashCode() {
		return Objects.hash(description, id, name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompanyDetailsDTO other = (CompanyDetailsDTO) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}
	public CompanyDetailsDTO(Long id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}
	public CompanyDetailsDTO() {
		super();
	}
	public CompanyDetailsDTO(FlightCompany company) {
		super();
		this.id = company.getId();
		this.name = company.getName();
		this.description = company.getDescription();
	}
	public CompanyDetailsDTO(HotelCompany company) {
		super();
		this.id = company.getId();
		this.name = company.getName();
		this.description = company.getDescription();
	}
	public CompanyDetailsDTO(ActivityCompany company) {
		super();
		this.id = company.getId();
		this.name = company.getName();
		this.description = company.getDescription();
	}
	
}
