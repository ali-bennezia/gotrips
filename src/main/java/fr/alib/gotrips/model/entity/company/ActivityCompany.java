package fr.alib.gotrips.model.entity.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.alib.gotrips.model.dto.CompanyDTO;
import fr.alib.gotrips.model.entity.Address;
import fr.alib.gotrips.model.entity.offers.Activity;
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
@Table( name = "ACTIVITYCOMPANY" )
public class ActivityCompany {

	@Id
	private Long id;
	@OneToOne(mappedBy = "activityCompany")
	private User user;
	@Column(nullable = false, unique = true)
	private String name;
	@Column(nullable = true, unique = false)
	private String description;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id")
	private List<Activity> activities = new ArrayList<Activity>();
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
	public List<Activity> getActivities() {
		return activities;
	}
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	@Override
	public int hashCode() {
		return Objects.hash(activities, address, description, id, name, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActivityCompany other = (ActivityCompany) obj;
		return Objects.equals(activities, other.activities) && Objects.equals(address, other.address)
				&& Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(user, other.user);
	}
	public ActivityCompany(Long id, User user, String name, String description, List<Activity> activities,
			Address address) {
		super();
		this.id = id;
		this.user = user;
		this.name = name;
		this.description = description;
		this.activities = activities;
		this.address = address;
	}
	public ActivityCompany(CompanyDTO dto, User user) {
		super();
		this.user = user;
		this.name = dto.getName();
		this.description = dto.getDescription();
		this.address = new Address(dto.getAddress());
	}
	public ActivityCompany() {
		super();
	}
	
	
	
}
