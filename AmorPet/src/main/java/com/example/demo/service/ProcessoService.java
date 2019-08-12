package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PerguntaDAO;
import com.example.demo.dao.ProcessoDAO;
import com.example.demo.dao.RespostaDAO;
import com.example.demo.model.Pergunta;
import com.example.demo.model.Processo;
import com.example.demo.model.Resposta;
import com.example.demo.model.Usuario;

@Service
public class ProcessoService {
	@Autowired
	private ProcessoDAO processoRep;
	@Autowired
	private PerguntaDAO perguntaRep;
	@Autowired
	private RespostaDAO respostaRep;
	
	public void save(Processo processo) {
		this.processoRep.save(processo);
	}
	
	public Processo criarProcesso(Usuario usuario) {
		Processo processo = new Processo();
		processo.setIdUsuario(usuario);
		processo.setRespostas(new ArrayList<Resposta>());
		processo.setDataRegistro(LocalDate.now());
		return processo;
	}

	public void salvarRespostasAndProcesso(Usuario usuario, List<Resposta> respostas) throws Exception {
		
		// todas as perguntas do bd
		List<Pergunta> perguntas = this.perguntaRep.findAll();

		Pergunta pergunta = null;
		// pega o processo do usuario
		Processo processo = this.processoRep.findByIdUsuario(usuario.getId()).get();
		
		for (Resposta resposta : respostas) {
			for(Pergunta p : perguntas) {
				if(p.getIdPergunta().equals(resposta.getIdPergunta().getIdPergunta())) {
					pergunta = p;
					break;
				}
			}
			if(pergunta == null) {
				throw new Exception("Pergunta não encontrada");
			}
			// fazer um for para iterar nas perguntas e ver se ela existe
			
			// lanca uma execao caso sejam diferentes
//			if(!this.compararTipoResidencia(pergunta, usuario)) {
//				throw new Exception("Tipo da residencia da pergunta diferente da que o usuário possui");
//			}
			
			// relaciona a resposta com a pergunta
			resposta.setIdPergunta(pergunta);
			
			// copia a pontuacao
			resposta.setPontuacaoPergunta(pergunta.getPontuacao());
			
			// relaciona a resposta a ao processo
			resposta.setIdProcesso(processo);
			this.respostaRep.save(resposta);
		}
		// relaciona o processo com as respostas
		processo.setRespostas(respostas);
		this.save(processo);
	}
	
	/**
	 * Compara se o tipo da residencia da pergunta é igual ao do usuário
	 * @param pergunta
	 * @param usuario
	 * @return true para tipos de residencias iguais ou todos, false para diferentes
	 */
	public boolean compararTipoResidencia(Pergunta pergunta, Usuario usuario) {
		if(pergunta.getResidencia().getTipoResidencia().equalsIgnoreCase("Todos")) {
			return true;
		}
		if(pergunta.getResidencia().getTipoResidencia()
				.equalsIgnoreCase(usuario.getEndereco().getResidencia().getTipoResidencia())) {
			return true;
		}
		return false;
	}
}
