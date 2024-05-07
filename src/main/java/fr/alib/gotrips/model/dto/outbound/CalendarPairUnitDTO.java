package fr.alib.gotrips.model.dto.outbound;

import java.util.Objects;

public class CalendarPairUnitDTO {
	private Long timestamp;
	private Boolean found;
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public Boolean getFound() {
		return found;
	}
	public void setFound(Boolean found) {
		this.found = found;
	}
	@Override
	public int hashCode() {
		return Objects.hash(found, timestamp);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CalendarPairUnitDTO other = (CalendarPairUnitDTO) obj;
		return Objects.equals(found, other.found) && Objects.equals(timestamp, other.timestamp);
	}
	public CalendarPairUnitDTO(Long timestamp, Boolean found) {
		super();
		this.timestamp = timestamp;
		this.found = found;
	}
	public CalendarPairUnitDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
