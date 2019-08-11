package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Embeddable
public class Endereco {

	@NotNull(message = "Residencia é necessário")
	@OneToOne
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "id_residencia")
	private Residencia residencia;

	@NotBlank(message = "Logradouro é necessário")
	@Column(length = 45)
	private String logradouro;

	@Digits(integer = 4, fraction = 0, message = "O número deve ter no máximo 4 digitos")
	@Column(name = "numero_casa", length = 4, columnDefinition = "INT(4)")
	private Integer numero;

	@NotBlank(message = "Complemento é necessário")
	@Column(length = 20)
	private String complemento;

	@NotBlank(message = "Bairro é necessário")
	@Column(length = 45)
	private String bairro;

	@NotBlank(message = "Cidade é necessário")
	@Column(length = 30)
	private String cidade;

	@NotNull(message = "Estado é necessário")
	@Enumerated(EnumType.STRING)
	@Column(length = 2)
	private Estado estado;

	@Digits(integer = 8, fraction = 0, message = "O CEP deve ter no máximo 8 digitos")
	@Column(length = 8, columnDefinition = "INT(8)")
	private Integer cep;

	public Residencia getResidencia() {
		return residencia;
	}

	public void setResidencia(Residencia residencia) {
		this.residencia = residencia;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
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

	public Integer getCep() {
		return cep;
	}

	public void setCep(Integer cep) {
		this.cep = cep;
	}

}
