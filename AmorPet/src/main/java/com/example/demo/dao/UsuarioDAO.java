package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Usuario;


public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {
	
	Usuario findUsuarioByEmail(String email);
	
	// CONSULTA SE OS DADOS DO USUÁRIO CONSTAM NO BD PARA FAZER LOGIN
	@Query("select u from Usuario u where u.email = :email and u.hashSenha = :hashSenha")
	Usuario efetuarLogin(String email, String hashSenha);

}
