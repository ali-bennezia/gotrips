package fr.alib.gotrips.model.dto.inbound;

import java.util.Objects;

public class PaymentDataDTO {
	private String creditCardName;
	private String creditCardNumber;
	private String creditCardCode;
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
	@Override
	public int hashCode() {
		return Objects.hash(address, creditCardCode, creditCardName, creditCardNumber);
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
				&& Objects.equals(creditCardNumber, other.creditCardNumber);
	}
	public PaymentDataDTO(String creditCardName, String creditCardNumber, String creditCardCode, AddressDTO address) {
		super();
		this.creditCardName = creditCardName;
		this.creditCardNumber = creditCardNumber;
		this.creditCardCode = creditCardCode;
		this.address = address;
	}
	public PaymentDataDTO() {
		super();
	}
	
}
