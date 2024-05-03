package fr.alib.gotrips.exception;

public class OfferNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OfferNotFoundException(String msg) {
		super(msg);
	}
	
	public OfferNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
