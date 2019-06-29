package com.example.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

@Entity
public class Usuario {

	@Id
	@GeneratedValue
	private Integer id;

	@NotBlank(message = "O campo precisa ser preenchido")
	@Column(length = 255, nullable = false)
	private String nome;

	@Email(message = "O campo precisa ser preenchido")
	@Column(length = 255, nullable = false)
	private String email;

	@NotEmpty(message = "Precisar ter uma senha valida")
	@Column(name = "hash_senha" ,length = 255, nullable = false)
	private String hashSenha;
	@NotEmpty
	@Transient
	private String confirmaSenha;

	@Pattern(regexp = "[0-9]{2}[9]{1}[0-9]{8}", message = "O número de telefone tem que ser valido")
	@Column(length = 11, nullable = false, columnDefinition = "char(11)")
	private String telefone;

	@Past(message = "A data tem que ser valida")
	@Temporal(TemporalType.DATE)
	@Column(name = "data_nasc")
	private Date dataNascimento;
	@NotEmpty
	@Column(length = 15, nullable = false)
	private String role;
	@Column(columnDefinition = "tinyint(1) default 1")
	private boolean Ativo;
	@Embedded
	private Endereco endereco;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHashSenha() {
		return hashSenha;
	}

	public void setHashSenha(String hashSenha) {
		this.hashSenha = hashSenha;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isAtivo() {
		return Ativo;
	}

	public void setAtivo(boolean ativo) {
		Ativo = ativo;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

}
