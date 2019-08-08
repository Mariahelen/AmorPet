package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AnimalDAO;
import com.example.demo.dao.ProcessoDAO;
import com.example.demo.dao.SelecaoDAO;
import com.example.demo.model.Animal;
import com.example.demo.model.Processo;
import com.example.demo.model.Selecao;
import com.example.demo.model.Usuario;

@Service
public class SelecaoService {
	@Autowired
	private SelecaoDAO selecaoRep;
	@Autowired
	private ProcessoDAO processoRep;
	@Autowired
	private AnimalDAO animalRep;

	public List<Selecao> lista() {
		return this.selecaoRep.findAll();
	}

	public Selecao findById(Integer id) {
		Optional<Selecao> selecao = this.selecaoRep.findById(id);
		if (selecao.isPresent()) {
			return selecao.get();
		}
		return null;
	}

	public Selecao findBySelecaoAberta(Integer idAnimal) {
		Optional<Selecao> selecao = this.selecaoRep.findByIdAnimal(idAnimal);
		if (selecao.isPresent()) {
			if (selecao.get().getAberta()) {
				return selecao.get();
			}
		}
		Selecao novaSelecao = new Selecao();
		novaSelecao.setProcessos(new ArrayList<Processo>());
		return novaSelecao;
	}

//	public void iniciarSelecao(Selecao selecao, Processo processo, Animal animal) {
//		selecao.getProcessos().add(processo);
//		selecao.setAnimal(animal);
//		selecao.setAberta(true);
//		if(selecao.getDataRegistro() == null) {
//			selecao.setDataRegistro(LocalDate.now());
//		}
//		this.selecaoRep.save(selecao);
//	}

	public void selecaoAnimal(Integer idAnimal, Usuario usuario) throws Exception {

		Selecao selecao = this.findBySelecaoAberta(idAnimal);
		// ARRUMAR DPS
		Optional<Animal> animal = this.animalRep.findById(idAnimal);
		if(!animal.isPresent()) {
			throw new Exception("Animal não cadastrado");
		}
		
		Processo processo = new Processo();
		Optional<Processo> processoExistente = this.processoRep.findByUsuario(usuario.getId());

		// verifica se existe um processo deste usuario
		if (processoExistente.isPresent()) {
			processo = processoExistente.get();
		} else {
			processo.setUsuario(usuario);
			processo.setSelecao(selecao);
		}

		// verifica se existe esse processo na selecao
		if (!selecao.getProcessos().contains(processo)) {
			selecao.getProcessos().add(processo);
		}
		if (selecao.getDataRegistro() == null) {
			selecao.setDataRegistro(LocalDate.now());
		}
		// ARRUMAR DPS
		selecao.setAnimal(animal.get());
		selecao.setAberta(true);
		this.selecaoRep.save(selecao);
	}
}
