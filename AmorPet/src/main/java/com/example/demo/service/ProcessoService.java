package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PerguntaDAO;
import com.example.demo.dao.ProcessoDAO;
import com.example.demo.dao.RespostaDAO;
import com.example.demo.dao.SelecaoDAO;
import com.example.demo.model.Animal;
import com.example.demo.model.Avaliacao;
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
	
	public List<Processo> lista() {
		return processoRep.findAll();
	}
	
	public List<Processo> lista(Usuario usuario) {
		return processoRep.findAllByUsuario(usuario);
	}
	
	public Selecao listarProcessos(Selecao selecao) {
		selecao.setProcessos(this.processoRep.findAllBySelecao(selecao));
		return selecao;
	}

	public void save(Processo processo) {
		this.processoRep.save(processo);
	}

	public void remove(Processo processo) {
		this.processoRep.delete(processo);
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
		processo.setDataRegistro(LocalDateTime.now());
		return processo;
	}

	public void salvarRespostasAndProcesso(Usuario usuario, Animal animal, List<Resposta> respostas) throws Exception {

		// todas as perguntas do bd
		List<Pergunta> perguntas = this.perguntaRep.findAll();

		Pergunta pergunta = null;
		// pega o processo do usuario
		Processo processo = this.processoRep.findByUsuarioByAnimal(usuario, animal).get();

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

			// salva a pontuacao
			resposta.setPontuacaoPergunta(pergunta.getPontuacao());
			// verifica se existe a confirmacao da pergunta
			if (resposta.getConfirmacaoPergunta() != null && resposta.getConfirmacaoPergunta().equals('N')) {
				pontuacaoTotal -= pergunta.getPontuacao();
			} else {
				// verifica se a pergunta foi respondida
				if (resposta.getRespostaPergunta() != null) {
					// seta a pontuacao com base na resposta
					if (resposta.getRespostaPergunta().equals('S')) {
						pontuacaoTotal += pergunta.getPontuacao();
					}else if(resposta.getRespostaPergunta().equals('N')) {
						pontuacaoTotal -= pergunta.getPontuacao();
					}
				}
			}

			// relaciona a resposta a ao processo
			resposta.setIdProcesso(processo);
			this.respostaRep.save(resposta);
		}
		if(pontuacaoTotal < 0) {
			pontuacaoTotal = 0;
		}
		// relaciona o processo com as respostas
		processo.setRespostas(respostas);
		processo.setPontuacaoFinal(pontuacaoTotal);
		this.save(processo);
	}
	
	public void salvarAvaliacaoAndProcesso(List<String> respostaPet, List<String> respostaDono, Integer idProcesso) throws Exception {
		Processo processo = this.findById(idProcesso);
		Avaliacao avaliacao = new Avaliacao();
		if(processo.getAvaliacao() == null) {
			processo.setAvaliacao(avaliacao);
		}
		// verifica o animalzinho
		if(respostaPet.get(0).equalsIgnoreCase("S")) {
			processo.getAvaliacao().setAvaliacaoLar(50);
			
		}else {
			processo.getAvaliacao().setAvaliacaoLar(0);
		}
		
		// verifica o dono
		if(respostaDono.get(0).equalsIgnoreCase("S")) {
			processo.getAvaliacao().setAvaliacaoDono(50);
			
		}else {
			processo.getAvaliacao().setAvaliacaoDono(0);
		}
		processo.getAvaliacao().setDataAvaliacao(LocalDateTime.now());
		this.processoRep.save(processo);
	}

	public void removerProcesso(Integer idSelecao, Integer idProcesso) throws Exception {
		Optional<Selecao> selecao = this.selecaoRep.findById(idSelecao);
		if (!selecao.isPresent()) {
			throw new Exception("Seleção não existe");
		}
		Processo processo = this.findById(idProcesso);
		if (selecao.get().getProcessos().contains(processo)) {
			processo.setIdSelecao(null);
			processo.setIdUsuario(null);
			selecao.get().getProcessos().remove(selecao.get().getProcessos().indexOf(processo));
			this.processoRep.delete(processo);
		}

		if (selecao.get().getProcessos().isEmpty()) {
			this.selecaoRep.delete(selecao.get());
			throw new Exception("Seleção vazia");
		}
	}

}
