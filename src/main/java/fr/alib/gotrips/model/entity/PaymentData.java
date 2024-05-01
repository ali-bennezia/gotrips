package fr.alib.gotrips.model.entity;

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
}
