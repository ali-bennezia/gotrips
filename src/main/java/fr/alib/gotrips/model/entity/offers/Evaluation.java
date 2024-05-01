package fr.alib.gotrips.model.entity.offers;

import fr.alib.gotrips.model.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

public class Evaluation {
	@Id
	private Long id;
	@OneToOne(mappedBy = "USER")
	private User user;
	
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String content;
	
	@ManyToOne(targetEntity = Flight.class, optional = true)
	private Flight flight;
	@ManyToOne(targetEntity = Hotel.class, optional = true)
	private Hotel hotel;
	@ManyToOne(targetEntity = Activity.class, optional = true)
	private Activity activity;
	
	@Column(nullable = false, precision = 1, scale = 0)
	private Integer note;
}
