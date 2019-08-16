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
import com.example.demo.model.Resposta;
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

	public Selecao findById(Integer id) throws Exception {
		Optional<Selecao> selecao = this.selecaoRep.findById(id);
		if (selecao.isPresent()) {
			return selecao.get();
		}
		throw new Exception("Seleção não encontrada");
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
		this.selecaoRep.save(selecao);
		selecao = this.findBySelecao(animal);
		
		processo.setIdSelecao(selecao);
		this.processoRep.save(processo);
		
		selecao.getProcessos().add(processo);
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

	public List<Resposta> buscaResposta(Selecao selecao, Integer idProcesso) throws Exception {
		Optional<Processo> processo = this.processoRep.findById(idProcesso);

		if(processo.isPresent()) {
			if(selecao.getProcessos().contains(processo.get())) {
				return processo.get().getRespostas();
			}
		}
		throw new Exception("Não existe");
	}
}
