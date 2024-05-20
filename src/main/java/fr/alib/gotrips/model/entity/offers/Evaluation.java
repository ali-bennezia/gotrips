package fr.alib.gotrips.model.entity.offers;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

import fr.alib.gotrips.model.dto.inbound.EvaluationDTO;
import fr.alib.gotrips.model.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table( name = "EVALUATION" )
public class Evaluation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(targetEntity = User.class)
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

	@Temporal(TemporalType.DATE)
	private Date createdAt;
	
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Integer getNote() {
		return note;
	}

	public void setNote(Integer note) {
		this.note = note;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public int hashCode() {
		return Objects.hash(activity, content, createdAt, flight, hotel, id, note, title, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evaluation other = (Evaluation) obj;
		return Objects.equals(activity, other.activity) && Objects.equals(content, other.content)
				&& Objects.equals(createdAt, other.createdAt) && Objects.equals(flight, other.flight)
				&& Objects.equals(hotel, other.hotel) && Objects.equals(id, other.id)
				&& Objects.equals(note, other.note) && Objects.equals(title, other.title)
				&& Objects.equals(user, other.user);
	}

	public void applyDTO(EvaluationDTO dto) {
		this.title = dto.getTitle();
		this.content = dto.getContent();
		this.note = dto.getNote();
		this.createdAt = Date.from(Instant.now());
	}
	
	public Evaluation(Long id, User user, String title, String content, Flight flight, Hotel hotel, Activity activity,
			Integer note, Date createdAt) {
		super();
		this.id = id;
		this.user = user;
		this.title = title;
		this.content = content;
		this.flight = flight;
		this.hotel = hotel;
		this.activity = activity;
		this.note = note;
		this.createdAt = createdAt;
	}
	
	public Evaluation(EvaluationDTO dto) {
		super();
		this.applyDTO(dto);
	}

	public Evaluation() {
		super();
	}
	
	
	
}
