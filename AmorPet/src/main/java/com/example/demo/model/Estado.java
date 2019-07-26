package com.example.demo.model;

public enum Estado {

	PE("Pernambuco"), SP("São Paulo"), RJ("Rio de Janeiro");
	
	private String nomeEstado;
	
	Estado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}
	
	public String getNomeEstado() {
		return nomeEstado;
	}

	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}
	
}
