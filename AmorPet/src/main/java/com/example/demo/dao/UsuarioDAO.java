package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {
	
	// CONSULTA SE OS DADOS DO USUÁRIO CONSTAM NO BD PARA FAZER LOGIN
	@Query("select u from Usuario u where u.email = :email and u.senha = :senha")
	
	public Usuario efetuarLogin(String email, String senha);

}
