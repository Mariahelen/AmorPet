package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Selecao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_selecao")
	private Integer idSelecao;
	@Column(name = "data_registro", nullable = false, columnDefinition = "DATETIME")
	private LocalDateTime dataRegistro;
	@OneToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "id_animal")
	private Animal idAnimal;
	@OneToMany(mappedBy = "idSelecao")
	@Cascade(CascadeType.ALL)
	private List<Processo> processos;
	@NotNull
	@Column(columnDefinition = "INT(3) default 1", nullable = false)
	private Integer situacao;

	public Integer getIdSelecao() {
		return idSelecao;
	}

	public void setIdSelecao(Integer idSelecao) {
		this.idSelecao = idSelecao;
	}

	public LocalDateTime getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(LocalDateTime dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public Animal getIdAnimal() {
		return idAnimal;
	}

	public void setIdAnimal(Animal idAnimal) {
		this.idAnimal = idAnimal;
	}

	public List<Processo> getProcessos() {
		return processos;
	}

	public void setProcessos(List<Processo> processos) {
		this.processos = processos;
	}

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

}
