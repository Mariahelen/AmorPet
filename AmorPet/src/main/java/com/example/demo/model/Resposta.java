package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Resposta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idResposta;
	@Column(length = 1, columnDefinition = "CHAR(1)", nullable = false)
	private Character respostaPergunta;
	@Column(length = 1, columnDefinition = "CHAR(1)")
	private Character confirmacaoPergunta;
	@Column(columnDefinition = "INT(3)", length = 3, nullable = false)
	private Integer pontuacaoPergunta;
	@ManyToOne
	@JoinColumn(name = "id_processo", insertable = true, updatable = true)
	@Fetch(FetchMode.JOIN)
	@Cascade(CascadeType.ALL)
	private Processo idProcesso;
	@OneToOne
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "id_pergunta")
	private Pergunta idPergunta;

	public Integer getIdResposta() {
		return idResposta;
	}

	public void setIdResposta(Integer idResposta) {
		this.idResposta = idResposta;
	}

	public Processo getIdProcesso() {
		return idProcesso;
	}

	public void setIdProcesso(Processo idProcesso) {
		this.idProcesso = idProcesso;
	}

	public Pergunta getIdPergunta() {
		return idPergunta;
	}

	public void setIdPergunta(Pergunta idPergunta) {
		this.idPergunta = idPergunta;
	}

	public Character getRespostaPergunta() {
		return respostaPergunta;
	}

	public void setRespostaPergunta(Character respostaPergunta) {
		this.respostaPergunta = respostaPergunta;
	}

	public Character getConfirmacaoPergunta() {
		return confirmacaoPergunta;
	}

	public void setConfirmacaoPergunta(Character confirmacaoPergunta) {
		this.confirmacaoPergunta = confirmacaoPergunta;
	}

	public Integer getPontuacaoPergunta() {
		return pontuacaoPergunta;
	}

	public void setPontuacaoPergunta(Integer pontuacaoPergunta) {
		this.pontuacaoPergunta = pontuacaoPergunta;
	}

}
