package fr.alib.gotrips.model.entity;

import java.util.Objects;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

import fr.alib.gotrips.model.dto.inbound.AddressDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;


@Embeddable
public class Address {
	@Column( nullable = false )
	private String street;
	@Column( nullable = false )
	private String city;
	@Column( nullable = false, precision = 6, scale = 0 )
	private Integer zipCode;
	@Column( nullable = false )
	private String country;
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getZipCode() {
		return zipCode;
	}
	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Address(String street, String city, Integer zipCode, String country) {
		super();
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
		this.country = country;
	}
	public Address(AddressDTO dto) {
		super();
		this.street = dto.getStreet();
		this.city = dto.getCity();
		this.zipCode = dto.getZipCode();
		this.country = dto.getCountry();
	}
	public Address() {
		super();
	}
	@Override
	public int hashCode() {
		return Objects.hash(city, country, street, zipCode);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		return Objects.equals(city, other.city) && Objects.equals(country, other.country)
				&& Objects.equals(street, other.street) && Objects.equals(zipCode, other.zipCode);
	}
	
	
}
