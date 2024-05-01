package fr.alib.gotrips.model.entity.offers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
	
}
