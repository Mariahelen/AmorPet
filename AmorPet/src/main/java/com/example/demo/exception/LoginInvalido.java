package com.example.demo.exception;

public class LoginInvalido extends Exception{
	
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "E-mail e/ou Senha inválido";
	}

}
