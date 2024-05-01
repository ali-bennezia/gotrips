package fr.alib.gotrips.model.entity.offers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import fr.alib.gotrips.model.entity.Address;
import fr.alib.gotrips.model.entity.company.HotelCompany;
import fr.alib.gotrips.model.entity.reservation.HotelReservation;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table( name = "HOTEL" )
public class Hotel {
	@Id
	private Long id;
	@ManyToOne(targetEntity = HotelCompany.class)
	private HotelCompany hotelCompany;
	@OneToMany(orphanRemoval = false)
	@JoinColumn(name="hotel_reservations_id", referencedColumnName = "id")
	private List<HotelReservation> activities = new ArrayList<HotelReservation>();
	@Column( precision = 3, scale = 0 )
	private Integer rooms;
	@Embedded
	private Address address;
	@Column( precision = 4, scale = 2 )
	private BigDecimal pricePerNight;
}
