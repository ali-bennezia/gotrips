package fr.alib.gotrips.model.entity.user;

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
}
