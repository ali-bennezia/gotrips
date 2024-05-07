package fr.alib.gotrips.model.dto.inbound;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

public class FlightReservationDTO {
	@NotNull
	private Integer cardId;

	public Integer getCardId() {
		return cardId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cardId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlightReservationDTO other = (FlightReservationDTO) obj;
		return Objects.equals(cardId, other.cardId);
	}

	public FlightReservationDTO(@NotNull Integer cardId) {
		super();
		this.cardId = cardId;
	}

	public FlightReservationDTO() {
		super();
	}
}
