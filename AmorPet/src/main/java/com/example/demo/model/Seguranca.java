package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

@Embeddable
public class Seguranca {

	
	@NotEmpty(message = "Senha é necessário")
	@Column(name = "hash_senha" ,length = 255, nullable = false)
	private String hashSenha;
	
	//@NotEmpty(message = "Confirmação é necessário")
	@Transient
	private String confirmaSenha;
	
	@Column(length = 15, nullable = false)
	private String role;
	
	@Column(columnDefinition = "tinyint(1) default 0", nullable = false)
	private boolean ativo;

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

	@Override
	public String toString() {
		return "Seguranca [hashSenha=" + hashSenha + ", confirmaSenha=" + confirmaSenha + ", role=" + role + ", ativo="
				+ ativo + "]";
	}

}
