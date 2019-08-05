package com.example.demo.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Avaliacao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_avaliacao")
	private Integer idAvaliacao;
	@Column(name = "avaliacao_lar", nullable = false)
	private Integer avaliacaoLar;
	@Column(name = "avaliacao_dono", nullable = false)
	private Integer avaliacaoDono;
	@Column(name = "data_avaliacao", nullable = false)
	private LocalDate dataAvaliacao;

	public Integer getIdAvaliacao() {
		return idAvaliacao;
	}

	public void setIdAvaliacao(Integer idAvaliacao) {
		this.idAvaliacao = idAvaliacao;
	}

	public Integer getAvaliacaoLar() {
		return avaliacaoLar;
	}

	public void setAvaliacaoLar(Integer avaliacaoLar) {
		this.avaliacaoLar = avaliacaoLar;
	}

	public Integer getAvaliacaoDono() {
		return avaliacaoDono;
	}

	public void setAvaliacaoDono(Integer avaliacaoDono) {
		this.avaliacaoDono = avaliacaoDono;
	}

	public LocalDate getDataAvaliacao() {
		return dataAvaliacao;
	}

	public void setDataAvaliacao(LocalDate dataAvaliacao) {
		this.dataAvaliacao = dataAvaliacao;
	}

}
