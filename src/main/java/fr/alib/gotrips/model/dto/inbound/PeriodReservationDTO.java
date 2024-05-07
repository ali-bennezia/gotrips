package fr.alib.gotrips.model.dto.inbound;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

public class PeriodReservationDTO {
	@NotNull
	private Integer cardId;
	@NotNull
	private Long beginTime;
	@NotNull
	private Long endTime;
	
	public Integer getCardId() {
		return cardId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}
	

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(beginTime, cardId, endTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PeriodReservationDTO other = (PeriodReservationDTO) obj;
		return Objects.equals(beginTime, other.beginTime) && Objects.equals(cardId, other.cardId)
				&& Objects.equals(endTime, other.endTime);
	}

	public PeriodReservationDTO(@NotNull Integer cardId, @NotNull Long beginTime, @NotNull Long endTime) {
		super();
		this.cardId = cardId;
		this.beginTime = beginTime;
		this.endTime = endTime;
	}

	public PeriodReservationDTO() {
		super();
	}


}
