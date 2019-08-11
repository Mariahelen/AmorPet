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

@Service
public class SelecaoService {
	@Autowired
	private SelecaoDAO selecaoRep;
	@Autowired
	private AnimalDAO animalRep;
	@Autowired
	private ProcessoDAO processoRep;

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

	/* CRIA UMA SELECAO OU USA A EXISTENTE */
	public void selecaoAnimal(Integer idAnimal, Processo processo) throws Exception {

		// verificar se o animal existe
		Animal animal = this.animalRep.findById(idAnimal).orElseThrow(Exception::new);
		
		Selecao selecao = this.findBySelecao(animal);
		
		// caso a seleção exista no bd
		if (selecao.getIdSelecao() != null) {
			
			for (Processo p : selecao.getProcessos()) {
				// se o usuario já estiver no processo da selecao
				if(p.getIdUsuario().getId().equals(processo.getIdUsuario().getId())) {
					return;
				}
			}
		}
		processo.setIdSelecao(selecao);
		selecao.getProcessos().add(processo);
		
		this.processoRep.save(processo);
		this.selecaoRep.save(selecao);
	}

	/* VERIFICAR SE EXISTE UMA SELECAO ABERTA OU CRIA UMA NOVA */
	public Selecao findBySelecao(Animal animal) {
		Optional<Selecao> selecao = this.selecaoRep.findByIdAnimal(animal.getIdAnimal());
		if (selecao.isPresent()) {
			// verifica se a selecao está aberta
			if (selecao.get().getAberta()) {
				return selecao.get();
			}
		}
		// cria uma nova selecao caso não tenha selecao aberta
		Selecao novaSelecao = this.novaSelecao(animal);
		return novaSelecao;
	}

	/* CRIA UMA NOVA SELECAO */
	public Selecao novaSelecao(Animal animal) {
		Selecao selecao = new Selecao();
		selecao.setIdAnimal(animal);
		selecao.setDataRegistro(LocalDate.now());
		selecao.setProcessos(new ArrayList<Processo>());
		selecao.setAberta(true);
		return selecao;
	}

}
