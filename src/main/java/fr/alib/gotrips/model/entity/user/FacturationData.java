package fr.alib.gotrips.model.entity.user;

import java.util.Objects;

import fr.alib.gotrips.model.entity.PaymentData;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table( name = "FACTURATIONDATA" )
public class FacturationData {
	@Id
	private Long id;
	@Embedded
	private PaymentData paymentData;
	
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
	@Override
	public int hashCode() {
		return Objects.hash(id, paymentData);
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
		return Objects.equals(id, other.id) && Objects.equals(paymentData, other.paymentData);
	}
	public FacturationData(Long id, PaymentData paymentData) {
		super();
		this.id = id;
		this.paymentData = paymentData;
	}
	public FacturationData() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "FacturationData [id=" + id + ", paymentData=" + paymentData + "]";
	}
	
	
	
}
