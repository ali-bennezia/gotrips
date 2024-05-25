package fr.alib.gotrips.model.entity.offers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.alib.gotrips.model.dto.inbound.HotelDTO;
import fr.alib.gotrips.model.entity.Address;
import fr.alib.gotrips.model.entity.company.HotelCompany;
import fr.alib.gotrips.model.entity.reservation.HotelReservation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;


@Entity
@Table( name = "HOTEL" )
public class Hotel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(targetEntity = HotelCompany.class)
	private HotelCompany hotelCompany;
	@OneToMany(orphanRemoval = false)
	@JoinColumn(name="hotel_reservations_id", referencedColumnName = "id")
	private List<HotelReservation> activities = new ArrayList<HotelReservation>();
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "hotel_evaluations_id", referencedColumnName = "id")
	private List<Evaluation> evaluations = new ArrayList<Evaluation>();
	@NotEmpty
	private String name;
	@NotEmpty
	private String description;
	@Column( precision = 3, scale = 0 )
	private Integer rooms;
	@Embedded
	private Address address;
	@Column( nullable = false, unique = false, precision = 2, scale = 1 )
	private BigDecimal averageEvaluation;
	@Column( precision = 4, scale = 2 )
	private BigDecimal pricePerNight;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public HotelCompany getHotelCompany() {
		return hotelCompany;
	}
	public void setHotelCompany(HotelCompany hotelCompany) {
		this.hotelCompany = hotelCompany;
	}
	public List<HotelReservation> getActivities() {
		return activities;
	}
	public void setActivities(List<HotelReservation> activities) {
		this.activities = activities;
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
	public Integer getRooms() {
		return rooms;
	}
	public void setRooms(Integer rooms) {
		this.rooms = rooms;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public List<Evaluation> getEvaluations() {
		return evaluations;
	}
	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}
	public BigDecimal getPricePerNight() {
		return pricePerNight;
	}
	public void setPricePerNight(BigDecimal pricePerNight) {
		this.pricePerNight = pricePerNight;
	}
	public BigDecimal getAverageEvaluation() {
		return averageEvaluation;
	}
	public void setAverageEvaluation(BigDecimal averageEvaluation) {
		this.averageEvaluation = averageEvaluation;
	}
	@Override
	public int hashCode() {
		return Objects.hash(activities, address, averageEvaluation, description, evaluations, hotelCompany, id, name,
				pricePerNight, rooms);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hotel other = (Hotel) obj;
		return Objects.equals(activities, other.activities) && Objects.equals(address, other.address)
				&& Objects.equals(averageEvaluation, other.averageEvaluation)
				&& Objects.equals(description, other.description) && Objects.equals(evaluations, other.evaluations)
				&& Objects.equals(hotelCompany, other.hotelCompany) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(pricePerNight, other.pricePerNight)
				&& Objects.equals(rooms, other.rooms);
	}
	public void applyDTO(HotelDTO dto)
	{
		this.name = dto.getName();
		this.description = dto.getDescription();
		this.rooms = dto.getRooms();
		this.address = new Address(dto.getAddress());
		this.pricePerNight = new BigDecimal( dto.getPricePerNight() );
	}
	public Hotel(Long id, HotelCompany hotelCompany, List<HotelReservation> activities, String name, String description, Integer rooms, Address address,
			BigDecimal pricePerNight) {
		super();
		this.id = id;
		this.hotelCompany = hotelCompany;
		this.activities = activities;
		this.name = name;
		this.description = description;
		this.rooms = rooms;
		this.address = address;
		this.pricePerNight = pricePerNight;
		this.averageEvaluation = new BigDecimal("0");
	}
	public Hotel(HotelDTO dto) {
		super();
		this.applyDTO(dto);
		this.averageEvaluation = new BigDecimal("0");
	}
	public Hotel() {
		super();
	}
}
