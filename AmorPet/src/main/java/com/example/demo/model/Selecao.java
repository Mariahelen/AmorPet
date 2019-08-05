package com.example.demo.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
	private Animal animal;
	@OneToMany(mappedBy = "selecao")
	@Cascade(CascadeType.ALL)
	private List<Processo> processos;

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
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public List<Processo> getProcessos() {
		return processos;
	}

	public void setProcessos(List<Processo> processos) {
		this.processos = processos;
	}

}
