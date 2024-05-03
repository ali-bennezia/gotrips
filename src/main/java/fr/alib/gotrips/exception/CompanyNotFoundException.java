package fr.alib.gotrips.exception;

public class CompanyNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CompanyNotFoundException(String msg) {
		super(msg);
	}
	
	public CompanyNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
