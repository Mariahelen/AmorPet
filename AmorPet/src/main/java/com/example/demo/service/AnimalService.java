package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AnimalDAO;
import com.example.demo.dao.SelecaoDAO;
import com.example.demo.dao.UsuarioDAO;
import com.example.demo.model.Animal;
import com.example.demo.model.Processo;
import com.example.demo.model.Selecao;
import com.example.demo.model.Usuario;

@Service
public class AnimalService {
	@Autowired
	private AnimalDAO animalRep;
	@Autowired
	private SelecaoDAO selecaoRep;
	@Autowired
	private UsuarioDAO usuarioRep;

	public Animal findById(Integer id) throws Exception {
		Optional<Animal> animal = this.animalRep.findById(id);
		if (animal.isPresent()) {
			return animal.get();
		}
		throw new Exception("Animal não encontrado");
	}

	public List<Animal> listarAnimais() throws Exception {
		List<Animal> animais = this.animalRep.findAllByDisponiveis(Sort.by("nome"));
		if (animais.isEmpty()) {
			throw new Exception();
		}
		return animais;
	}

	public void criarAnimal(Animal animal) {
		this.animalRep.save(animal);
	}

	public void removerAnimal(Integer idAdm, String senhaAdm, Integer idAnimal) throws Exception {

		if (this.usuarioRep.existsByIdAndByHashSenha(idAdm, senhaAdm)) {

			this.animalRep.deleteById(idAnimal);
		} else {
			throw new Exception("Não foi possivel remover");
		}

	}

	public void editarAnimal(Animal animal) {
		Animal animalBanco = animalRep.getOne(animal.getIdAnimal());
		animalBanco.setDataNascimento(animal.getDataNascimento());
		animalBanco.setHistoriaAnimal(animal.getHistoriaAnimal());
		animalBanco.setNome(animal.getNome());
		animalBanco.setSexoAnimal(animal.getSexoAnimal());
		this.animalRep.save(animalBanco);
	}

	public boolean verificarDono(Animal animal) {
		if (animal.getUsuario() != null) {
			return true;
		}
		return false;
	}

	public boolean verificarSeDisponivel(Animal animal, Usuario usuario) {
		if(animal.getUsuario() != null) {
			return false;
		}
		Optional<Selecao> selecao = this.selecaoRep.findByIdAnimal(animal.getIdAnimal());
		if (selecao.isPresent()) {
			if (selecao.get().getProcessos().size() >= 10) {
				if (usuario == null) {
					return false;
				}
				for (Processo p : selecao.get().getProcessos()) {
					if (!p.getIdUsuario().getId().equals(usuario.getId())) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
