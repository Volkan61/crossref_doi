package com.zahangirbd.server.excep;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DoiItemNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1019687707798333645L;
	public DoiItemNotFoundException(String exception) {
	    super(exception);
	  }
}
