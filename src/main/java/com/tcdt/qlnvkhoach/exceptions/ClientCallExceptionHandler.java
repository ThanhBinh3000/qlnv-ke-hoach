package com.tcdt.qlnvkhoach.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClientCallExceptionHandler extends RuntimeException {
	private static final long serialVersionUID = -291011096353901722L;

	@ResponseBody
	@ExceptionHandler(value = ClientCallException.class)
	public ResponseEntity<?> handleException(ClientCallException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
	}
}
