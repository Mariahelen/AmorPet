package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dao.RespostaDAO;
import com.example.demo.model.Pergunta;
import com.example.demo.model.Resposta;

@Service
public class RespostaService {

	@Autowired
	private RespostaDAO respostaRep;

	public List<Resposta> listar() {
		return this.respostaRep.findAll(Sort.by("pontuacao"));
	}

	/**
	 * Recebe uma lista da classe Pergunta
	 * @return Retorna uma lista de respostas relacionadas com as perguntas e com tamanho definido na quantidade de perguntas.
	 */
	public List<Resposta> criarListaRespostas(List<Pergunta> listaPerguntas) {
		List<Resposta> respostas = new ArrayList<>(listaPerguntas.size());
		// relaciona cada pergunta a uma resposta
		for (Pergunta pergunta : listaPerguntas) {
			respostas.add(new Resposta());
			// seta pergunta e pontuacao da ultima resposta
			respostas.get(respostas.size() - 1).setIdPergunta(pergunta);
			respostas.get(respostas.size() - 1).setPontuacaoPergunta(pergunta.getPontuacao());
		}
		return respostas;
	}
	
	public List<Resposta> criarListaRespostasUsuario(Resposta[] respostasUsuario) {
		List<Resposta> respostas = new ArrayList<>(respostasUsuario.length);
		for (Resposta resposta : respostasUsuario) {
			respostas.add(resposta);
		}
		return respostas;
	}
}
