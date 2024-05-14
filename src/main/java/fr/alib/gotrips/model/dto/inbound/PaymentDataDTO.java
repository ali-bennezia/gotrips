package fr.alib.gotrips.model.dto.inbound;

import java.util.Objects;

import fr.alib.gotrips.model.dto.duplex.AddressDTO;
import jakarta.validation.constraints.NotEmpty;

public class PaymentDataDTO {
	@NotEmpty
	private String creditCardName;
	@NotEmpty
	private String creditCardNumber;
	@NotEmpty
	private String creditCardCode;
	private Long expirationTime;
	private AddressDTO address;
	public String getCreditCardName() {
		return creditCardName;
	}
	public void setCreditCardName(String creditCardName) {
		this.creditCardName = creditCardName;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public String getCreditCardCode() {
		return creditCardCode;
	}
	public void setCreditCardCode(String creditCardCode) {
		this.creditCardCode = creditCardCode;
	}
	public AddressDTO getAddress() {
		return address;
	}
	public void setAddress(AddressDTO address) {
		this.address = address;
	}
	public Long getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(Long expirationTime) {
		this.expirationTime = expirationTime;
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, creditCardCode, creditCardName, creditCardNumber, expirationTime);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaymentDataDTO other = (PaymentDataDTO) obj;
		return Objects.equals(address, other.address) && Objects.equals(creditCardCode, other.creditCardCode)
				&& Objects.equals(creditCardName, other.creditCardName)
				&& Objects.equals(creditCardNumber, other.creditCardNumber)
				&& Objects.equals(expirationTime, other.expirationTime);
	}
	public PaymentDataDTO(@NotEmpty String creditCardName, @NotEmpty String creditCardNumber,
			@NotEmpty String creditCardCode, @NotEmpty Long expirationTime, AddressDTO address) {
		super();
		this.creditCardName = creditCardName;
		this.creditCardNumber = creditCardNumber;
		this.creditCardCode = creditCardCode;
		this.expirationTime = expirationTime;
		this.address = address;
	}
	public PaymentDataDTO() {
		super();
	}
}
