package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PerguntaDAO;
import com.example.demo.dao.ProcessoDAO;
import com.example.demo.dao.RespostaDAO;
import com.example.demo.dao.SelecaoDAO;
import com.example.demo.model.Pergunta;
import com.example.demo.model.Processo;
import com.example.demo.model.Resposta;
import com.example.demo.model.Selecao;
import com.example.demo.model.Usuario;
import com.example.demo.util.Util;

@Service
public class ProcessoService {
	@Autowired
	private ProcessoDAO processoRep;
	@Autowired
	private PerguntaDAO perguntaRep;
	@Autowired
	private RespostaDAO respostaRep;
	@Autowired
	private SelecaoDAO selecaoRep;

	public void save(Processo processo) {
		this.processoRep.save(processo);
	}

	public void remove(Processo processo) {
		this.processoRep.delete(processo);
		;
	}

	public Processo findById(Integer id) throws Exception {
		Optional<Processo> processo = this.processoRep.findById(id);
		if (processo.isPresent()) {
			return processo.get();
		}
		throw new Exception("Processo não existe");
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

		int pontuacaoTotal = 0;
		for (Resposta resposta : respostas) {
			for (Pergunta p : perguntas) {
				if (p.getIdPergunta().equals(resposta.getIdPergunta().getIdPergunta())) {
					pergunta = p;
					break;
				}
			}
			if (pergunta == null) {
				throw new Exception("Pergunta não encontrada");
			}
			// fazer um for para iterar nas perguntas e ver se ela existe

			// lanca uma execao caso sejam diferentes
			if (!Util.compararTipoResidencia(pergunta, usuario)) {
				throw new Exception("Tipo da residencia da pergunta diferente da que o usuário possui");
			}

			// relaciona a resposta com a pergunta
			resposta.setIdPergunta(pergunta);

			// copia a pontuacao
			resposta.setPontuacaoPergunta(pergunta.getPontuacao());
			pontuacaoTotal += pergunta.getPontuacao();

			// relaciona a resposta a ao processo
			resposta.setIdProcesso(processo);
			this.respostaRep.save(resposta);
		}
		// relaciona o processo com as respostas
		processo.setRespostas(respostas);
		processo.setPontuacaoFinal(pontuacaoTotal);
		this.save(processo);
	}

	public void removerProcesso(Integer idSelecao, Integer idProcesso) throws Exception {
		Optional<Selecao> selecao = this.selecaoRep.findById(idSelecao);
		if(!selecao.isPresent()) {
			throw new Exception("Seleção não existe");
		}
		Processo processo = this.findById(idProcesso);
		if(selecao.get().getProcessos().contains(processo)) {
			this.remove(processo);
		}
		if(selecao.get().getProcessos().isEmpty()) {
			this.selecaoRep.delete(selecao.get());
		}
	}

}
