package com.example.demo.model;

public enum Pontuacao {
	MAXIMA(20), ALTA(15), MEDIA(10), BAIXA(5);
	
	public Integer pontos;
	Pontuacao(Integer p) {
		this.pontos = p;
	}
}
