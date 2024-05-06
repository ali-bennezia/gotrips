package fr.alib.gotrips.model.entity;

import java.util.Objects;

import org.springframework.security.crypto.encrypt.TextEncryptor;

import fr.alib.gotrips.model.dto.inbound.PaymentDataDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class PaymentData {
	@Column(nullable = false, unique = false)
	private String creditCardNameEncrypted;
	@Column(nullable = false, unique = false)
	private String creditCardNumberEncrypted;
	@Column(nullable = false, unique = false)
	private String creditCardCodeEncrypted;
	@Embedded
	private Address address;
	public String getCreditCardNameEncrypted() {
		return creditCardNameEncrypted;
	}
	public void setCreditCardNameEncrypted(String creditCardNameEncrypted) {
		this.creditCardNameEncrypted = creditCardNameEncrypted;
	}
	public String getCreditCardNumberEncrypted() {
		return creditCardNumberEncrypted;
	}
	public void setCreditCardNumberEncrypted(String creditCardNumberEncrypted) {
		this.creditCardNumberEncrypted = creditCardNumberEncrypted;
	}
	public String getCreditCardCodeEncrypted() {
		return creditCardCodeEncrypted;
	}
	public void setCreditCardCodeEncrypted(String creditCardCodeEncrypted) {
		this.creditCardCodeEncrypted = creditCardCodeEncrypted;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, creditCardCodeEncrypted, creditCardNameEncrypted, creditCardNumberEncrypted);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaymentData other = (PaymentData) obj;
		return Objects.equals(address, other.address)
				&& Objects.equals(creditCardCodeEncrypted, other.creditCardCodeEncrypted)
				&& Objects.equals(creditCardNameEncrypted, other.creditCardNameEncrypted)
				&& Objects.equals(creditCardNumberEncrypted, other.creditCardNumberEncrypted);
	}
	public void applyDTO(PaymentDataDTO dto, TextEncryptor encryptor) {
		this.creditCardNameEncrypted = encryptor.encrypt(dto.getCreditCardName());
		this.creditCardNumberEncrypted = encryptor.encrypt(dto.getCreditCardNumber());
		this.creditCardCodeEncrypted = encryptor.encrypt(dto.getCreditCardCode());
		this.address = new Address(dto.getAddress());
	}
	public PaymentData(String creditCardNameEncrypted, String creditCardNumberEncrypted, String creditCardCodeEncrypted,
			Address address) {
		super();
		this.creditCardNameEncrypted = creditCardNameEncrypted;
		this.creditCardNumberEncrypted = creditCardNumberEncrypted;
		this.creditCardCodeEncrypted = creditCardCodeEncrypted;
		this.address = address;
	}
	public PaymentData(PaymentDataDTO dto, TextEncryptor encryptor) {
		super();
		this.applyDTO(dto, encryptor);
	}
	public PaymentData() {
		super();
	}
	
	
}
