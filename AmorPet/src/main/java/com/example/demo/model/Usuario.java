package com.example.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.util.Util;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "Nome é necessário")
	@Column(length = 255, nullable = false)
	private String nome;

	@Email(message = "Email inválido")
	@NotEmpty(message = "Email é necessário")
	@Column(length = 255, nullable = false)
	private String email;

	@NotBlank(message = "Senha é necessário")
	@Column(name = "hash_senha", length = 255, nullable = false)
	private String hashSenha;
	@NotBlank(message = "Confirmação é necessário")
	@Transient
	private String confirmaSenha;

	@Pattern(regexp = "[0-9] {11}", message = "Telefone inválido, o padrão é (xx)x.xxxx-xxxx")
	@Size(max = 11, min = 11, message = "Telefone inválido, o padrão é (xx)x.xxxx-xxxx")
	@Column(length = 11, nullable = false, columnDefinition = "char(11)")
	private String telefone;

	@NotNull(message = "Data de Nascimento é necessária")
	@Past(message = "Deve ser uma data de nascimento válida")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "data_nasc")
	private Date dataNascimento;
	@Column(length = 50)
	private String caminhoFoto;
	@Column(length = 15, nullable = false)
	private String role;
	@Column(columnDefinition = "tinyint(1) default 0", nullable = false)
	private boolean ativo;
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
		this.telefone = Util.limparMascaraTelefone(telefone);
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCaminhoFoto() {
		return caminhoFoto;
	}

	public void setCaminhoFoto(String caminhoFoto) {
		this.caminhoFoto = caminhoFoto;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
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
				+ ", caminhoFoto=" + caminhoFoto + ", role=" + role + ", ativo=" + ativo
				+ ", endereco=" + endereco + "]";
	}

}
