package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.UsuarioDAO;
import com.example.demo.exception.LoginInvalido;
import com.example.demo.model.Usuario;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UsuarioService {

	@Autowired
	private UsuarioDAO usuarioDAO;

	public Usuario save(Usuario usuario) {
		return this.usuarioDAO.save(usuario);
	}

	public Usuario findById(Integer id) throws Exception {
		return this.usuarioDAO.findById(id).orElseThrow(Exception::new);
	}

	public Usuario findByEmail(String email) {
		Usuario usuarioBanco = this.usuarioDAO.findByEmail(email);
		if (usuarioBanco != null) {
			return usuarioBanco;
		}
		return null;
	}

	public void criarUsuario(Usuario usuario) throws Exception {

		if (this.usuarioDAO.findByEmail(usuario.getEmail()) != null) {
			throw new Exception("Email já cadastrado");

		} else if (!usuario.getHashSenha().equals(usuario.getConfirmaSenha())) {
			throw new Exception("As senhas não coincidem!");
		}

		// USUARIO ESTARÁ ATIVO AUTOMATICAMENTE POR ENQUANTO
		// REMOVER ESTAS LINHAS ASSIM QUE TIVER ENVIO DE EMAIL
		usuario.setAtivo(true);

//	    usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		this.save(usuario);
	}

	public Usuario efetuarLogin(String email, String senha) throws LoginInvalido {
		Usuario usuario = this.usuarioDAO.efetuarLogin(email, senha);
		if (usuario == null || !usuario.isAtivo()) {
			throw new LoginInvalido();
		}
		return usuario;
	}

	public Usuario editarPerfil(Usuario usuarioForm) throws Exception {
		Usuario usuarioParaSalvar = this.findById(usuarioForm.getId());
		usuarioParaSalvar.setNome(usuarioForm.getNome());
		usuarioParaSalvar.setDataNascimento(usuarioForm.getDataNascimento());
		usuarioParaSalvar.setEmail(usuarioParaSalvar.getEmail());
		usuarioParaSalvar.setTelefone(usuarioForm.getTelefone());
		usuarioParaSalvar.setEndereco(usuarioForm.getEndereco());
		usuarioParaSalvar.setHashSenha(usuarioParaSalvar.getHashSenha());
		usuarioParaSalvar.setConfirmaSenha(usuarioParaSalvar.getHashSenha());
		return usuarioParaSalvar;
	}

}
