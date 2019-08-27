package com.example.demo.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.ResidenciaDAO;
import com.example.demo.dao.UsuarioDAO;
import com.example.demo.exception.IdadeInvalidaException;
import com.example.demo.exception.LoginInvalido;
import com.example.demo.exception.SenhaInvalidaException;
import com.example.demo.model.Endereco;
import com.example.demo.model.Residencia;
import com.example.demo.model.Usuario;
import com.example.demo.util.Util;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UsuarioService {

	@Autowired
	private UsuarioDAO usuarioDAO;
	@Autowired
	private ResidenciaDAO residenciaRep;

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

	public void criarUsuario(Usuario usuario) throws SenhaInvalidaException, IdadeInvalidaException, Exception {

		if (this.usuarioDAO.findByEmail(usuario.getEmail()) != null) {
			throw new Exception("Email já cadastrado");

		} else if (!usuario.getHashSenha().equals(usuario.getConfirmaSenha())) {
			throw new SenhaInvalidaException();
			
		}else if(Util.calcularIdade(usuario.getDataNascimento()) < 18) {
			throw new IdadeInvalidaException();
		}
		usuario.setHashSenha(Util.criptografarSenha(usuario.getHashSenha()));
		// USUARIO ESTARÁ ATIVO AUTOMATICAMENTE POR ENQUANTO
		// REMOVER ESTAS LINHAS ASSIM QUE TIVER ENVIO DE EMAIL
		usuario.setAtivo(true);

		this.save(usuario);
	}

	public Usuario efetuarLogin(String email, String senha) throws LoginInvalido, NoSuchAlgorithmException, UnsupportedEncodingException {
		Usuario usuario = this.usuarioDAO.efetuarLogin(email, Util.criptografarSenha(senha));
		if (usuario == null || !usuario.isAtivo()) {
			throw new LoginInvalido();
		}
		return usuario;
	}

	public Usuario editarPerfil(Usuario usuarioForm) throws IdadeInvalidaException, Exception {
		Usuario usuarioParaSalvar = this.findById(usuarioForm.getId());
		usuarioParaSalvar.setNome(usuarioForm.getNome());
		if(Util.calcularIdade(usuarioForm.getDataNascimento()) < 18) {
			throw new IdadeInvalidaException();
		}
		usuarioParaSalvar.setDataNascimento(usuarioForm.getDataNascimento());
		usuarioParaSalvar.setEmail(usuarioParaSalvar.getEmail());
		usuarioParaSalvar.setTelefone(usuarioForm.getTelefone());
		
		Optional<Residencia> residencia = this.residenciaRep.findById(usuarioForm.getEndereco().getResidencia().getIdResidencia());
		if(residencia.isPresent()) {
			if(! residencia.get().getTipoResidencia().equalsIgnoreCase("TODOS")) {
				usuarioParaSalvar.setEndereco(usuarioForm.getEndereco());
				
			}else {
				throw new Exception("Residencia inválida");
			}
		}else {
			throw new Exception("Residencia não encontrada");
		}
		
		usuarioParaSalvar.setHashSenha(usuarioParaSalvar.getHashSenha());
		usuarioParaSalvar.setConfirmaSenha(usuarioParaSalvar.getHashSenha());
		return usuarioParaSalvar;
	}

	public boolean verificarEndereco(Endereco endereco) {
		if (endereco.getBairro() == null
				|| endereco.getCep() == null
				|| endereco.getCidade() == null
				|| endereco.getComplemento() == null
				|| endereco.getLogradouro() == null
				|| endereco.getResidencia() == null
				|| endereco.getNumero() == null) {
			
			return true;
		}
		if(endereco.getBairro().trim().isEmpty()
				|| endereco.getCidade().trim().isEmpty()
				|| endereco.getComplemento().trim().isEmpty()
				|| endereco.getLogradouro().trim().isEmpty()
				|| endereco.getResidencia().getTipoResidencia().trim().isEmpty()
				|| endereco.getResidencia().getTipoResidencia().equalsIgnoreCase("TODOS")) {
			
			return true;
		}
		
		return false;
	}

}
