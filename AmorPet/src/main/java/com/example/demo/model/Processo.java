package com.example.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Processo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_processo")
	private Integer idProcesso;
	@Column(length = 1, columnDefinition = "CHAR(1)")
	private Character resposta;
	@Column(length = 1, columnDefinition = "CHAR(1)")
	private Character confirmacao;
	@Column(length = 3)
	private Integer pontuacao;
	@OneToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	@ManyToOne
	@JoinColumn(name = "id_selecao", insertable = true, updatable = true)
	@Fetch(FetchMode.JOIN)
	@Cascade(CascadeType.ALL)
	private Selecao selecao;
	@OneToOne
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "id_avaliacao")
	private Avaliacao avaliacao;
	@ManyToMany
	@JoinTable(name = "processo_has_pergunta", joinColumns = @JoinColumn(name = "id_processo"), inverseJoinColumns = @JoinColumn(name = "id_pergunta"))
	@Column(name = "id_pergunta")
	private List<Pergunta> perguntas;

	public Integer getIdProcesso() {
		return idProcesso;
	}

	public void setIdProcesso(Integer idProcesso) {
		this.idProcesso = idProcesso;
	}

	public Character getResposta() {
		return resposta;
	}

	public void setResposta(Character resposta) {
		this.resposta = resposta;
	}

	public Character getConfirmacao() {
		return confirmacao;
	}

	public void setConfirmacao(Character confirmacao) {
		this.confirmacao = confirmacao;
	}

	public Integer getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Integer pontuacao) {
		this.pontuacao = pontuacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Selecao getSelecao() {
		return selecao;
	}

	public void setSelecao(Selecao selecao) {
		this.selecao = selecao;
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	public List<Pergunta> getPerguntas() {
		return perguntas;
	}

	public void setPerguntas(List<Pergunta> perguntas) {
		this.perguntas = perguntas;
	}

}
