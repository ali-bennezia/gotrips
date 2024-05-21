package fr.alib.gotrips.model.dto.outbound;

import java.util.Objects;

import fr.alib.gotrips.model.entity.offers.Evaluation;

public class EvaluationDetailsDTO {
	private Long id;
	private String title;
	private String content;
	private Integer note;
	private UserDetailsDTO author;
	private Long createdAtTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Integer getNote() {
		return note;
	}
	public void setNote(Integer note) {
		this.note = note;
	}
	public UserDetailsDTO getAuthor() {
		return author;
	}
	public void setAuthor(UserDetailsDTO author) {
		this.author = author;
	}
	public Long getCreatedAtTime() {
		return createdAtTime;
	}
	public void setCreatedAtTime(Long createdAtTime) {
		this.createdAtTime = createdAtTime;
	}
	@Override
	public int hashCode() {
		return Objects.hash(author, content, createdAtTime, id, note, title);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EvaluationDetailsDTO other = (EvaluationDetailsDTO) obj;
		return Objects.equals(author, other.author) && Objects.equals(content, other.content)
				&& Objects.equals(createdAtTime, other.createdAtTime) && Objects.equals(id, other.id)
				&& Objects.equals(note, other.note) && Objects.equals(title, other.title);
	}
	public EvaluationDetailsDTO(Long id, String title, String content, Integer note, UserDetailsDTO author, Long createdAtTime) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.note = note;
		this.author = author;
		this.createdAtTime = createdAtTime;
	}
	public EvaluationDetailsDTO(Evaluation eval) {
		super();
		this.id = eval.getId();
		this.title = eval.getTitle();
		this.content = eval.getContent();
		this.note = eval.getNote();
		this.author = new UserDetailsDTO(eval.getUser());
		this.createdAtTime = eval.getCreatedAt().getTime();
	}
	public EvaluationDetailsDTO() {
		super();
	}
	
}
