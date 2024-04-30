package fr.alib.gotrips.model.entity.company;

import java.util.ArrayList;
import java.util.List;

import fr.alib.gotrips.model.entity.User;
import fr.alib.gotrips.model.entity.offers.Activity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
	@OneToOne(mappedBy = "USER")
	private User user;
	@Column(nullable = false, unique = true)
	private String name;
	@Column(nullable = true, unique = false)
	private String description;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id")
	private List<Activity> activities = new ArrayList<Activity>();
	
}
