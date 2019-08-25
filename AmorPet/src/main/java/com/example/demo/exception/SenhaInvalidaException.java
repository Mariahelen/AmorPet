package com.example.demo.exception;

public class SenhaInvalidaException extends Exception {

	@Override
	public String getMessage() {
		return "Senhas não são iguais";
	}
}
