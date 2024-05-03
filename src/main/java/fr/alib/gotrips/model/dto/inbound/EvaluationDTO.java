package fr.alib.gotrips.model.dto.inbound;

import java.util.Objects;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class EvaluationDTO {
	@NotEmpty
	private String title;
	@NotEmpty
	private String content;
	@Min(0)
	@Max(10)
	private Integer note;
	
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
	@Override
	public int hashCode() {
		return Objects.hash(content, note, title);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EvaluationDTO other = (EvaluationDTO) obj;
		return Objects.equals(content, other.content) && Objects.equals(note, other.note)
				&& Objects.equals(title, other.title);
	}
	public EvaluationDTO(@NotEmpty String title, @NotEmpty String content, @Min(0) @Max(10) Integer note) {
		super();
		this.title = title;
		this.content = content;
		this.note = note;
	}
	public EvaluationDTO() {
		super();
	}
	
	
}
