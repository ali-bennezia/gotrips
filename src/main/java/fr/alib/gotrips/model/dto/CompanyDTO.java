package fr.alib.gotrips.model.dto;

import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CompanyDTO {
	@NotEmpty
	private String name;
	@NotEmpty
	private String description;
	@NotNull
	@Valid
	private AddressDTO address;
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
	@Override
	public int hashCode() {
		return Objects.hash(address, description, name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompanyDTO other = (CompanyDTO) obj;
		return Objects.equals(address, other.address) && Objects.equals(description, other.description)
				&& Objects.equals(name, other.name);
	}
	public CompanyDTO(@NotEmpty String name, @NotEmpty String description, @NotNull @Valid AddressDTO address) {
		super();
		this.name = name;
		this.description = description;
		this.address = address;
	}
	public CompanyDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
