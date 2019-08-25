package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	@Column(length = 3, columnDefinition = "INT(3)")
	private Integer pontuacaoFinal;
	@Column(name = "data_registro", columnDefinition = "DATETIME")
	private LocalDateTime dataRegistro;
	@OneToOne
	@JoinColumn(name = "id_usuario")
	private Usuario idUsuario;
	@ManyToOne
	@JoinColumn(name = "id_selecao", insertable = true, updatable = true)
	@Fetch(FetchMode.JOIN)
	@Cascade(CascadeType.MERGE)
	private Selecao idSelecao;
	@OneToOne
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "id_avaliacao")
	private Avaliacao avaliacao;
	@OneToMany(mappedBy = "idProcesso")
	@Cascade(CascadeType.ALL)
	private List<Resposta> respostas;

	public Integer getIdProcesso() {
		return idProcesso;
	}

	public void setIdProcesso(Integer idProcesso) {
		this.idProcesso = idProcesso;
	}

	public Integer getPontuacaoFinal() {
		return pontuacaoFinal;
	}

	public void setPontuacaoFinal(Integer pontuacaoFinal) {
		this.pontuacaoFinal = pontuacaoFinal;
	}

	public LocalDateTime getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(LocalDateTime dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public Usuario getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Usuario idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Selecao getIdSelecao() {
		return idSelecao;
	}

	public void setIdSelecao(Selecao idSelecao) {
		this.idSelecao = idSelecao;
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	public List<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
	}

}
