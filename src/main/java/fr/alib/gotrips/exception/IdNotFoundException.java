package fr.alib.gotrips.exception;

import org.springframework.security.core.AuthenticationException;

public class IdNotFoundException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public IdNotFoundException(String msg) {
		super(msg);
	}

}
