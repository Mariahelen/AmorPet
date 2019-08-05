package com.example.demo.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Pergunta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pergunta")
	private Integer idPergunta;
	@Column(name = "descricao_pergunta", length = 50, nullable = false)
	private String descricaoPergunta;
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "pontuacao")
	private Pontuacao pontuacao;
	@Column(name = "data_registro", nullable = false)
	private LocalDate dataRegistro;
	@OneToOne
	@Cascade(CascadeType.ALL)
	private Residencia residencia;
	@OneToOne
	private Usuario usuarioAdm;

	public Integer getIdPergunta() {
		return idPergunta;
	}

	public void setIdPergunta(Integer idPergunta) {
		this.idPergunta = idPergunta;
	}

	public String getDescricaoPergunta() {
		return descricaoPergunta;
	}

	public void setDescricaoPergunta(String descricaoPergunta) {
		this.descricaoPergunta = descricaoPergunta;
	}

	public Pontuacao getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Pontuacao pontuacao) {
		this.pontuacao = pontuacao;
	}

	public LocalDate getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(LocalDate dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public Residencia getResidencia() {
		return residencia;
	}

	public void setResidencia(Residencia residencia) {
		this.residencia = residencia;
	}

	public Usuario getUsuarioAdm() {
		return usuarioAdm;
	}

	public void setUsuarioAdm(Usuario usuarioAdm) {
		this.usuarioAdm = usuarioAdm;
	}

}
