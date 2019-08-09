package com.example.demo.model;

import java.time.LocalDate;
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
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Selecao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_selecao")
	private Integer idSelecao;
	@DateTimeFormat(pattern = "yy-MM-dd")
	@Column(name = "data_registro", nullable = false)
	private LocalDate dataRegistro;
	@OneToOne
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "id_animal")
	private Animal idAnimal;
	@OneToMany(mappedBy = "idSelecao")
	@Cascade(CascadeType.ALL)
	private List<Processo> processos;
	@NotNull
	private Boolean aberta;

	public Integer getIdSelecao() {
		return idSelecao;
	}

	public void setIdSelecao(Integer idSelecao) {
		this.idSelecao = idSelecao;
	}

	public LocalDate getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(LocalDate dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public Animal getAnimal() {
		return idAnimal;
	}

	public void setAnimal(Animal idAnimal) {
		this.idAnimal = idAnimal;
	}

	public List<Processo> getProcessos() {
		return processos;
	}

	public void setProcessos(List<Processo> processos) {
		this.processos = processos;
	}

	public Boolean getAberta() {
		return aberta;
	}

	public void setAberta(Boolean aberta) {
		this.aberta = aberta;
	}

}
