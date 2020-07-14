package com.zahangirbd.server.excep;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DoiItemInputErrorException extends RuntimeException {
	private static final long serialVersionUID = 1019687707798333645L;
	public DoiItemInputErrorException(String exception) {
	    super(exception);
	  }
}
