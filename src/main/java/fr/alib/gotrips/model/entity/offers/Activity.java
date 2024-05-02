package fr.alib.gotrips.model.entity.offers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.alib.gotrips.model.entity.Address;
import fr.alib.gotrips.model.entity.company.ActivityCompany;
import fr.alib.gotrips.model.entity.reservation.ActivityReservation;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table( name = "ACTIVITY" )
public class Activity {

	@Id
	private Long id;
	@ManyToOne(targetEntity = ActivityCompany.class)
	private ActivityCompany activityCompany;
	
	@OneToMany(orphanRemoval = false)
	@JoinColumn(name="activity_reservations_id", referencedColumnName = "id")
	private List<ActivityReservation> activities = new ArrayList<ActivityReservation>();
	
	@Column( nullable = false, unique = true )
	private String title;
	@Column( nullable = false, unique = false )
	private String description;
	
	@Embedded
	private Address address;
	@Column( nullable = false )
	private BigDecimal pricePerDay;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ActivityCompany getActivityCompany() {
		return activityCompany;
	}
	public void setActivityCompany(ActivityCompany activityCompany) {
		this.activityCompany = activityCompany;
	}
	public List<ActivityReservation> getActivities() {
		return activities;
	}
	public void setActivities(List<ActivityReservation> activities) {
		this.activities = activities;
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
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public BigDecimal getPricePerDay() {
		return pricePerDay;
	}
	public void setPricePerDay(BigDecimal pricePerDay) {
		this.pricePerDay = pricePerDay;
	}
	@Override
	public int hashCode() {
		return Objects.hash(activities, activityCompany, address, description, id, pricePerDay, title);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		return Objects.equals(activities, other.activities) && Objects.equals(activityCompany, other.activityCompany)
				&& Objects.equals(address, other.address) && Objects.equals(description, other.description)
				&& Objects.equals(id, other.id) && Objects.equals(pricePerDay, other.pricePerDay)
				&& Objects.equals(title, other.title);
	}
	public Activity(Long id, ActivityCompany activityCompany, List<ActivityReservation> activities, String title,
			String description, Address address, BigDecimal pricePerDay) {
		super();
		this.id = id;
		this.activityCompany = activityCompany;
		this.activities = activities;
		this.title = title;
		this.description = description;
		this.address = address;
		this.pricePerDay = pricePerDay;
	}
	public Activity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
