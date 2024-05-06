package fr.alib.gotrips.model.entity.user;

import java.util.Objects;

import org.springframework.security.crypto.encrypt.TextEncryptor;

import fr.alib.gotrips.model.dto.inbound.PaymentDataDTO;
import fr.alib.gotrips.model.entity.PaymentData;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table( name = "FACTURATIONDATA" )
public class FacturationData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Embedded
	private PaymentData paymentData;
	@ManyToOne(targetEntity = User.class)
	private User user;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PaymentData getPaymentData() {
		return paymentData;
	}
	public void setPaymentData(PaymentData paymentData) {
		this.paymentData = paymentData;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, paymentData, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FacturationData other = (FacturationData) obj;
		return Objects.equals(id, other.id) && Objects.equals(paymentData, other.paymentData)
				&& Objects.equals(user, other.user);
	}
	public void applyDTO(PaymentDataDTO dto, TextEncryptor encryptor)
	{
		this.paymentData = new PaymentData(dto, encryptor);
	}
	public FacturationData(Long id, PaymentData paymentData) {
		super();
		this.id = id;
		this.paymentData = paymentData;
	}
	public FacturationData(PaymentDataDTO dto, TextEncryptor encryptor) {
		super();
		this.applyDTO(dto, encryptor);
	}
	public FacturationData() {
		super();
	}
	@Override
	public String toString() {
		return "FacturationData [id=" + id + ", paymentData=" + paymentData + "]";
	}
	
	
	
}
