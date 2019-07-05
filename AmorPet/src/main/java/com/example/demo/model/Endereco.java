package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Embeddable
public class Endereco {

	@NotBlank(message = "Logradouro é necessário")
	@Column(length = 45)
	private String logradouro;
	
	@Size(max = 4, message = "Tamanho máximo de 4 números")
	@Column(name = "numero_casa" ,length = 4)
	private int numero;
	
	@NotBlank(message = "Complemento é necessário")
	@Column(length = 20)
	private String complemento;
	
	@NotBlank(message = "Bairro é necessário")
	@Column(length = 45)
	private String bairro;
	
	@NotBlank(message = "Cidade é necessário")
	@Column(length = 30)
	private String cidade;
	
	@NotEmpty(message = "Estado é necessário")
	@Enumerated(EnumType.STRING)
	@Column(length = 2)
	private Estado estado;
	
	@Size(max = 8, message = "CEP inválido")
	@Column(length = 8)
	private int cep;

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public int getCep() {
		return cep;
	}

	public void setCep(int cep) {
		this.cep = cep;
	}

}
