package com.example.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Embeddable
public class DadosPessoais {

	@NotBlank(message = "Nome é necessário")
	@Column(length = 255, nullable = false)
	private String nome;
	
	
	@NotNull(message = "Data de Nascimento é necessária")
	@Past(message = "Deve ser uma data de nascimento válida")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "data_nasc")
	private Date dataNascimento;

	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@Override
	public String toString() {
		return "DadosPessoais [nome=" + nome + ", dataNascimento=" + dataNascimento + "]";
	}
	
	
}
