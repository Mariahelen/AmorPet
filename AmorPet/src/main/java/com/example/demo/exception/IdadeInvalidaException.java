package com.example.demo.exception;

public class IdadeInvalidaException extends Exception {

	@Override
	public String getMessage() {
		return "Idade minima é de 18 anos";
	}
}
