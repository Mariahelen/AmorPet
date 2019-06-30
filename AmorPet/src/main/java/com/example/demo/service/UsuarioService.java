package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UsuarioDAO;
import com.example.demo.exception.LoginInvalido;
import com.example.demo.model.Usuario;


@Service
public class UsuarioService {

	@Autowired
	private UsuarioDAO usuarioDAO;

	public void criarUsuario(Usuario usuario) throws Exception {

		if (this.usuarioDAO.findUsuarioByEmail(usuario.getEmail()) != null) {
			throw new Exception("Já existe usuário com este e-mail: " + usuario.getEmail());

		} else if (!usuario.getHashSenha().equals(usuario.getConfirmaSenha())) {
			throw new Exception("As senhas não coincidem!");
		}
		
		// USUARIO ESTARÁ ATIVO AUTOMATICAMENTE POR ENQUANTO
		// REMOVER ESTAS LINHAS ASSIM QUE TIVER ENVIO DE EMAIL
		usuario.setAtivo(true);
		
//	    usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuarioDAO.save(usuario);
	}

	public Usuario efetuarLogin(String email, String senha) throws LoginInvalido {
		Usuario usuario = this.usuarioDAO.efetuarLogin(email, senha);
		if (usuario == null) {
			throw new LoginInvalido();
		}
		if (!usuario.isAtivo()) {
			throw new LoginInvalido();
		}
		return usuario;
	}

}
