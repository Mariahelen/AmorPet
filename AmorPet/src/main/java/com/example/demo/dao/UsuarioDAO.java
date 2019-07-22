package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Usuario;


public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {
	
	@Query("select u from Usuario u where u.email = :email")
	Usuario findByEmail(String email);
	
	// CONSULTA SE OS DADOS DO USUÁRIO CONSTAM NO BD PARA FAZER LOGIN
	@Query("select u from Usuario u where u.email = :email and u.hashSenha = :hashSenha")
	Usuario efetuarLogin(String email, String hashSenha);

	@Query("select u from Usuario u where u.id = :id and u.telefone = :telefone")
	Usuario findByIdAndTelefone(Integer id, String telefone);
	
	@Query("select count(u)>0 from Usuario u where u.id = :idAdm and u.hashSenha = :hashSenha")
	boolean existsByIdAndByHashSenha(Integer idAdm, String hashSenha);
	
}
