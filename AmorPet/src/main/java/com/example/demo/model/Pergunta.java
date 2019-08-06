package com.example.demo.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Pergunta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pergunta")
	private Integer idPergunta;
	
	@NotBlank(message = "Descrição da pergunta é necessária")
	@Size(max = 50, message = "A descrição deve ter no máximo 50 caracteres")
	@Column(name = "descricao_pergunta", length = 50, nullable = false)
	private String descricaoPergunta;
	
	@NotNull(message = "Prioridade é necessária")
	@Column(name = "pontuacao")
	private Integer pontuacao;
	
	@Column(name = "data_registro", nullable = false)
	private LocalDate dataRegistro;
	
	@OneToOne
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "id_residencia")
	private Residencia residencia;
	
	@OneToOne
	@JoinColumn(name = "id_administrador")
	private Usuario usuarioAdm;
	
	@OneToOne
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "id_pergunta_titular")
	private Pergunta perguntaTitular;

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

	public Integer getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Integer pontuacao) {
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

	public Pergunta getPerguntaTitular() {
		return perguntaTitular;
	}

	public void setPerguntaTitular(Pergunta perguntaTitular) {
		this.perguntaTitular = perguntaTitular;
	}

}
