package com.example.demo.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Embeddable
public class Endereco {

	@NotBlank(message = "Campo necessário")
	private String logradouro;
	@NotBlank(message = "Campo necessário")
	private int numero;
	@NotBlank(message = "Campo necessário")
	private String complemento;
	@NotBlank(message = "Campo necessário")
	private String bairro;
	@NotBlank(message = "Campo necessário")
	private String cidade;
	@NotBlank(message = "Campo neecssário")
	private Estado estado;
	@Pattern(regexp = "[0-9]{5}-[09]")
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
