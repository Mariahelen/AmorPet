package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PerguntaDAO;
import com.example.demo.model.Pergunta;

@Service
public class PerguntaService {
	@Autowired
	private PerguntaDAO perguntaRep;
	
	public List<Pergunta> listar() {
		return this.perguntaRep.findAll();
	}
	
	public void save(Pergunta pergunta) {
		this.perguntaRep.save(pergunta);
	}
}
