package com.example.demo.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UsuarioDAO;
import com.example.demo.exception.LoginInvalido;
import com.example.demo.model.Contato;
import com.example.demo.model.DadosPessoais;
//import com.example.demo.model.Seguranca;
import com.example.demo.model.Usuario;
import com.example.demo.util.Utilidade;


@Service
public class UsuarioService {

	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private HttpSession session;
	
	public Usuario save(Usuario usuario) {
		return this.usuarioDAO.save(usuario);
	}
	
	public Usuario usuarioDaSessao() {
		return (Usuario) session.getAttribute("usuarioLogado");
	}
	
	public void criarUsuario(Usuario usuario) throws Exception {

		if (this.usuarioDAO.findByContatoByEmail(usuario.getContato().getEmail()) != null) {
			throw new Exception("Email já cadastrado");

		} else if (!usuario.getSeguranca().getHashSenha().equals(usuario.getSeguranca().getConfirmaSenha())) {
			throw new Exception("As senhas não coincidem!");
		}
		
		// USUARIO ESTARÁ ATIVO AUTOMATICAMENTE POR ENQUANTO
		// REMOVER ESTAS LINHAS ASSIM QUE TIVER ENVIO DE EMAIL
		usuario.getSeguranca().setAtivo(true);
		
//	    usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		this.save(usuario);
	}

	public Usuario efetuarLogin(String email, String senha) throws LoginInvalido {
		Usuario usuario = this.usuarioDAO.efetuarLogin(email, senha);
		if (usuario == null || !usuario.getSeguranca().isAtivo()) {
			throw new LoginInvalido();
		}
		return usuario;
	}
	
	
	public void salvarDadosPessoais(DadosPessoais dp) throws Exception {
		
		Usuario usuario =  this.usuarioDaSessao();
		
		if(usuario == null) {
			
			throw new Exception();
		}
		
		usuario.setDadosPessoais(dp);
		this.save(usuario);
	}
	
	//ALTERAR TELEFONE
public void salvarTelefone(String telefone) throws LoginInvalido, Exception {
		
		Usuario usuarioDaSessao =  this.usuarioDaSessao();
		telefone = Utilidade.limparMascaraTelefone(telefone);
		
		Usuario usuarioDoBanco = this.usuarioDAO.findByTelefoneAndId(telefone, usuarioDaSessao.getId());
		
		if(usuarioDaSessao == null) {
			throw new LoginInvalido();
		}
		if(usuarioDoBanco == null) {
			
			throw new Exception();
		}
		
		usuarioDaSessao.getContato().setTelefone(telefone);
		this.save(usuarioDaSessao);
	}
	
	// SALVAR CONTATO EDITADO 
		public void salvarContato(Contato c) throws Exception {
			
		Usuario usuario = this.usuarioDAO.findByContatoByEmail(c.getEmail());
		if(usuario == null) {
			throw new Exception();
		}
		
		if(this.usuarioDAO.findByContatoByEmail(c.getNovoEmail()) != null) {
			
			throw new Exception();
		}
		
		usuario.getContato().setEmail(c.getNovoEmail());
		session.setAttribute("usuarioLogado", usuario);
		this.save(usuario);

	}
		
		// SALVAR CONTATO EDITADO 
//				public void salvarSenha(Seguranca s) throws Exception {
//					
//				Usuario usuario =  (Usuario) session.getAttribute("usuarioLogado");
//				
//				if(usuario == null) {
//					throw new Exception();
//				}
//				
//				if(this.usuarioDAO.findBySegurancaByHashSenha(s.getHashSenha()) != null) {
//					
//					throw new Exception();
//				}
//				
//				usuario.getContato().setEmail(c.getNovoEmail());
//				
//				this.usuarioDAO.save(usuario);
//
//			}
//		
		
		
}
