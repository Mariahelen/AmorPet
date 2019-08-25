package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Embeddable
public class Endereco {

	@NotNull(message = "Residencia é necessário")
	@OneToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "id_residencia")
	private Residencia residencia;

	@NotBlank(message = "Logradouro é necessário")
	@Column(length = 60)
	private String logradouro;

	@Size(max = 20, min = 0, message = "Tamanho máximo é 20 caracteres")
	@Column(name = "numero_casa", length = 20)
	private String numero;

	@NotBlank(message = "Complemento é necessário")
	@Column(length = 255)
	private String complemento;

	@NotBlank(message = "Bairro é necessário")
	@Column(length = 45)
	private String bairro;

	@NotBlank(message = "Cidade é necessário")
	@Column(length = 30)
	private String cidade;

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

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
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

	public Integer getCep() {
		return cep;
	}

	public void setCep(Integer cep) {
		this.cep = cep;
	}

}
