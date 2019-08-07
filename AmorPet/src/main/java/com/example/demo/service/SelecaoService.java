package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.SelecaoDAO;
import com.example.demo.model.Animal;
import com.example.demo.model.Processo;
import com.example.demo.model.Selecao;

@Service
public class SelecaoService {
	@Autowired
	private SelecaoDAO selecaoRep;
	
	public List<Selecao> lista() {
		return this.selecaoRep.findAll();
	}
	
	public Selecao findById(Integer id) {
		Optional<Selecao> selecao = this.selecaoRep.findById(id);
		if(selecao.isPresent()) {
			return selecao.get();
		}
		return null;
	}
	
	public Selecao findBySelecaoAberta(Integer idAnimal) {
		Optional<Selecao> selecao = this.selecaoRep.findByIdAnimal(idAnimal);
		if(selecao.isPresent()) {
			if(selecao.get().getAberta()) {
				return selecao.get();
			}
		}
		Selecao novaSelecao = new Selecao();
		novaSelecao.setProcessos(new ArrayList<Processo>());
		return novaSelecao;
	}
	
	public void iniciarSelecao(Selecao selecao, Processo processo, Animal animal) {
		selecao.getProcessos().add(processo);
		selecao.setAnimal(animal);
		selecao.setAberta(true);
		if(selecao.getDataRegistro() == null) {
			selecao.setDataRegistro(LocalDate.now());
		}
		this.selecaoRep.save(selecao);
	}
}
