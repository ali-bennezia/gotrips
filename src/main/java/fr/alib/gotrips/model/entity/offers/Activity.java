package fr.alib.gotrips.model.entity.offers;

import java.math.BigDecimal;

import fr.alib.gotrips.model.entity.Address;
import fr.alib.gotrips.model.entity.company.ActivityCompany;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table( name = "ACTIVITY" )
public class Activity {

	@Id
	private Long id;
	@ManyToOne(targetEntity = ActivityCompany.class)
	private ActivityCompany activityCompany;
	@Column( nullable = false, unique = true )
	private String title;
	@Column( nullable = false, unique = false )
	private String description;
	@Embedded
	private Address address;
	@Column( nullable = false )
	private BigDecimal pricePerDay;
	
}
