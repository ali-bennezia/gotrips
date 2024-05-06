package fr.alib.gotrips.model.dto.outbound;

import java.util.Objects;

import org.springframework.security.crypto.encrypt.TextEncryptor;

import fr.alib.gotrips.model.dto.duplex.AddressDTO;
import fr.alib.gotrips.model.entity.user.FacturationData;

public class CardDetailsDTO {
	private Long id;
	private String name;
	private String partialCardNumber;
	private AddressDTO address;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPartialCardNumber() {
		return partialCardNumber;
	}
	public void setPartialCardNumber(String partialCardNumber) {
		this.partialCardNumber = partialCardNumber;
	}
	public AddressDTO getAddress() {
		return address;
	}
	public void setAddress(AddressDTO address) {
		this.address = address;
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, id, name, partialCardNumber);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CardDetailsDTO other = (CardDetailsDTO) obj;
		return Objects.equals(address, other.address) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(partialCardNumber, other.partialCardNumber);
	}
	public CardDetailsDTO(Long id, String name, String partialCardNumber, AddressDTO address) {
		super();
		this.id = id;
		this.name = name;
		this.partialCardNumber = partialCardNumber;
		this.address = address;
	}
	public CardDetailsDTO(FacturationData data, TextEncryptor encryptor) {
		super();
		this.id = data.getId();
		this.name = encryptor.decrypt(data.getPaymentData().getCreditCardNameEncrypted());
		this.partialCardNumber = encryptor.decrypt(data.getPaymentData().getCreditCardNumberEncrypted()).replaceAll(".{12}$", "XXXXXXXXXXXX");
		this.address = new AddressDTO(data.getPaymentData().getAddress());
	}
	public CardDetailsDTO() {
		super();
	}
	
}
