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

	/* CRIA UMA SELECAO OU USA A EXISTENTE */
	public void selecaoAnimal(Integer idAnimal, Usuario usuario) throws Exception {

		Selecao selecao = this.findBySelecao(idAnimal);
		Processo processo = new Processo();

		// caso a seleção exista no bd
		if (selecao.getIdSelecao() != null) {
			
			for (Processo p : selecao.getProcessos()) {
				// se o usuario já estiver no processo da selecao
				if(p.getUsuario().getId().equals(usuario.getId())) {
					processo = p;
					return;
				}
			}
		}
		// criar um processo caso o usuario não estiver
		processo.setUsuario(usuario);
		processo.setSelecao(selecao);
		selecao.getProcessos().add(processo);

		this.processoRep.save(processo);
		this.selecaoRep.save(selecao);
	}

	/* VERIFICAR SE EXISTE UMA SELECAO ABERTA OU CRIA UMA NOVA */
	public Selecao findBySelecao(Integer idAnimal) throws Exception {
		Optional<Selecao> selecao = this.selecaoRep.findByIdAnimal(idAnimal);
		if (selecao.isPresent()) {
			// verifica se a selecao está aberta
			if (selecao.get().getAberta()) {
				return selecao.get();
			}
		}
		// cria uma nova selecao caso não tenha selecao aberta
		Animal animal = this.animalRep.findById(idAnimal).orElseThrow(Exception::new);
		Selecao novaSelecao = this.novaSelecao(animal);
		return novaSelecao;
	}

	/* CRIA UMA NOVA SELECAO */
	public Selecao novaSelecao(Animal animal) {
		Selecao selecao = new Selecao();
		selecao.setAnimal(animal);
		selecao.setDataRegistro(LocalDate.now());
		selecao.setProcessos(new ArrayList<Processo>());
		selecao.setAberta(true);
		return selecao;
	}

}
