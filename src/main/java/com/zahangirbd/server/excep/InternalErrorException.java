package com.zahangirbd.server.excep;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalErrorException extends RuntimeException {
	private static final long serialVersionUID = 1019687707798333645L;
	public InternalErrorException(String exception) {
	    super(exception);
	  }
}
