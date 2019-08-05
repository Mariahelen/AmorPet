package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Residencia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_residencia")
	private Integer idResidencia;
	@Column(name = "tipo_residencia", length = 15, nullable = false)
	private String tipoResidencia;

	public Integer getIdResidencia() {
		return idResidencia;
	}

	public void setIdResidencia(Integer idResidencia) {
		this.idResidencia = idResidencia;
	}

	public String getTipoResidencia() {
		return tipoResidencia;
	}

	public void setTipoResidencia(String tipoResidencia) {
		this.tipoResidencia = tipoResidencia;
	}

}
