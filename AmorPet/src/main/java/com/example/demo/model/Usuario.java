package com.example.demo.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.util.Utilidade;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@NotBlank(message = "O campo precisa ser preenchido")
	@Column(length = 255, nullable = false)
	private String nome;

	@Email(message = "O campo precisa ser preenchido")
	@Column(length = 255, nullable = false)
	private String email;

	@NotEmpty(message = "Senha é necessário")
	@Column(name = "hash_senha" ,length = 255, nullable = false)
	private String hashSenha;
	@NotEmpty(message = "Confirmação de senha é necessário")
	@Transient
	private String confirmaSenha;

	@Size(max = 11, message = "Telefone inválido, o padrão é (DD)9.xxxx-xxxx")
	@Column(length = 11, nullable = false, columnDefinition = "char(11)")
	private String telefone;

	@Past(message = "A data tem que ser válida")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "data_nasc")
	private LocalDate dataNascimento;
	@Column(length = 15)
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
		this.telefone = Utilidade.limparMascaraTelefone(telefone);
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
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

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + ", hashSenha=" + hashSenha
				+ ", confirmaSenha=" + confirmaSenha + ", telefone=" + telefone + ", dataNascimento=" + dataNascimento
				+ ", role=" + role + ", Ativo=" + Ativo + ", endereco=" + endereco + "]";
	}

}
