package com.cts.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TokenExpirationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TokenExpirationException(String msg) {
		super(msg);
	}

}
