package com.example.demo.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Entity
public class Animal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idAnimal;

	@OneToOne
	@JoinColumn(name = "id_administrador")
	private Usuario usuarioAdm;
	@ManyToOne
	@JoinColumn(name = "id_usuario", insertable = true, updatable = true)
	@Fetch(FetchMode.JOIN)
	@Cascade(CascadeType.ALL)
	private Usuario usuario;
	
	@NotBlank(message = "Nome do animal é necessário")
	@Column(length = 255, nullable = false)
	private String nome;

	@NotNull(message = "Data de Nascimento do é necessária")
	@PastOrPresent(message = "Deve ser uma data de nascimento válida")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "data_nasc")
	private LocalDate dataNascimento;

	@NotNull(message = "O sexo do animal é necessário")
	@Column(length = 10, nullable = false)
	@Size(max = 10)
	private String sexoAnimal;

	@NotNull(message = "A história do animal é necessário")
	@Column(length = 500, nullable = false)
	@Size(max = 500)
	private String historiaAnimal;

	@Column(length = 255)
	@Size(max = 255)
	private String caminhoFoto;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataRegistro;

	@NotNull(message = "O porte do animal é necessário")
	@Column(length = 10, nullable = false)
	@Size(max = 10)
	private String porteAnimal;

	@NotNull(message = "O tipo do animal é necessário")
	@Column(length = 10, nullable = false)
	@Size(max = 10)
	private String tipoAnimal;

	@Transient
	private MultipartFile file;

	public Integer getIdAnimal() {
		return idAnimal;
	}

	public void setIdAnimal(Integer idAnimal) {
		this.idAnimal = idAnimal;
	}

	public Usuario getUsuarioAdm() {
		return usuarioAdm;
	}

	public void setUsuarioAdm(Usuario usuarioAdm) {
		this.usuarioAdm = usuarioAdm;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSexoAnimal() {
		return sexoAnimal;
	}

	public void setSexoAnimal(String sexoAnimal) {
		this.sexoAnimal = sexoAnimal;
	}

	public String getHistoriaAnimal() {
		return historiaAnimal;
	}

	public void setHistoriaAnimal(String historiaAnimal) {
		this.historiaAnimal = historiaAnimal;
	}

	public String getCaminhoFoto() {
		return caminhoFoto;
	}

	public void setCaminhoFoto(String caminhoFoto) {
		this.caminhoFoto = caminhoFoto;
	}

	public LocalDate getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(LocalDate dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public String getPorteAnimal() {
		return porteAnimal;
	}

	public void setPorteAnimal(String porteAnimal) {
		this.porteAnimal = porteAnimal;
	}

	public String getTipoAnimal() {
		return tipoAnimal;
	}

	public void setTipoAnimal(String tipoAnimal) {
		this.tipoAnimal = tipoAnimal;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
