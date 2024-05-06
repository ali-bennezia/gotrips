package fr.alib.gotrips.model.dto.inbound;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

public class ReservationDTO {
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
		ReservationDTO other = (ReservationDTO) obj;
		return Objects.equals(cardId, other.cardId);
	}

	public ReservationDTO(@NotNull Integer cardId) {
		super();
		this.cardId = cardId;
	}

	public ReservationDTO() {
		super();
	}
}
