package fr.alib.gotrips.model.entity.reservation;

import java.math.BigDecimal;
import java.sql.Timestamp;

import fr.alib.gotrips.model.entity.PaymentData;
import fr.alib.gotrips.model.entity.offers.Activity;
import fr.alib.gotrips.model.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table( name = "ACTIVITYRESERVATION" )
public class ActivityReservation {

	@Id
	private Long id;
	@ManyToOne(targetEntity = User.class)
	private User user;
	@ManyToOne(targetEntity = Activity.class, optional = true)
	private Activity activity;
	@Column(nullable = false, precision = 8, scale = 2)
	private BigDecimal price;
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp paymentTime;
	@Embedded
	private PaymentData paymentData;
	
}
