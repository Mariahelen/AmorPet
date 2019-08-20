package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dao.RespostaDAO;
import com.example.demo.model.Animal;
import com.example.demo.model.Pergunta;
import com.example.demo.model.Resposta;
import com.example.demo.model.Usuario;
import com.example.demo.util.Util;

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
	 * @throws Exception  Caso tipo de residencia seja diferente da do usuário
	 */
	public List<Resposta> criarListaRespostas(List<Pergunta> listaPerguntas, Usuario usuario, Integer idAnimal) {
		
		List<Resposta> respostas = this.respostaRep.findByRespostas(usuario, idAnimal);
		if(!respostas.isEmpty()) {
			return respostas;
		}
		respostas = new ArrayList<>(listaPerguntas.size());
		// relaciona cada pergunta a uma resposta
		for (Pergunta pergunta : listaPerguntas) {
			// verifica se o tipo da residencia é diferente
			if(! Util.compararTipoResidencia(pergunta, usuario)) {
				continue;
			}
			respostas.add(new Resposta());
			// seta pergunta e pontuacao da ultima resposta
			respostas.get(respostas.size() - 1).setIdPergunta(pergunta);
			respostas.get(respostas.size() - 1).setPontuacaoPergunta(pergunta.getPontuacao());
		}
		return respostas;
	}
	
	public List<Resposta> criarListaRespostasUsuario(Resposta[] respostasUsuario, Usuario usuario, Animal animal) {
		
		List<Resposta> respostas = this.respostaRep.findByRespostas(usuario, animal.getIdAnimal());
		
		if(!respostas.isEmpty()) {
			int i = 0;
			for (Resposta resposta : respostas) {
				resposta.setRespostaPergunta(respostasUsuario[i].getRespostaPergunta());
				i++;
			}
			return respostas;
		}
		
		respostas = new ArrayList<>(respostasUsuario.length);
		for (Resposta resposta : respostasUsuario) {
			respostas.add(resposta);
		}
		return respostas;
	}

	public List<Resposta> criarListaConfirmacoesUsuario(Resposta[] respostasUsuario, Usuario usuario, Animal animal) {
		
		List<Resposta> respostas = this.respostaRep.findByRespostas(usuario, animal.getIdAnimal());
		
		if(!respostas.isEmpty()) {
			int i = 0;
			for (Resposta resposta : respostas) {
				resposta.setConfirmacaoPergunta(respostasUsuario[i].getConfirmacaoPergunta());
				i++;
			}
			return respostas;
		}
		
		respostas = new ArrayList<>(respostasUsuario.length);
		for (Resposta resposta : respostasUsuario) {
			respostas.add(resposta);
		}
		return respostas;
	}
}
