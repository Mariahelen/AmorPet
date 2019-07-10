package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.example.demo.util.Utilidade;

@Embeddable
public class Contato {
	
	@NotEmpty(message = "Email é necessário")
	@Column(length = 255, nullable = false)
	private String email;
	
	@Column(length = 255)
	private String novoEmail;
	
	@Size(max = 11, min = 11, message = "Telefone inválido, o padrão é (xx)x.xxxx-xxxx")
	@Column(length = 11, nullable = false, columnDefinition = "char(11)")
	private String telefone;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = Utilidade.limparMascaraTelefone(telefone);
	}

	public String getNovoEmail() {
		return novoEmail;
	}

	public void setNovoEmail(String novoEmail) {
		this.novoEmail = novoEmail;
	}

	@Override
	public String toString() {
		return "Contato [email=" + email + ", novoEmail=" + novoEmail + ", telefone=" + telefone + "]";
	}
	
}
