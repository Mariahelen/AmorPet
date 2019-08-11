package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AnimalDAO;
import com.example.demo.dao.UsuarioDAO;
import com.example.demo.model.Animal;

@Service
public class AnimalService {
	@Autowired
	private AnimalDAO animalRep;
	
	@Autowired
	private UsuarioDAO usuarioRep;
	
	public Animal findById(Integer id) throws Exception {
		Optional<Animal> animal = this.animalRep.findById(id);
		if(animal.isPresent()) {
			return animal.get();
		}
		throw new Exception("Animal não encontrado");
	}

	public List<Animal> listarAnimais() throws Exception {
		List<Animal> animais = this.animalRep.findAll(Sort.by("nome"));
		if (animais.isEmpty()) {
			throw new Exception();
		}
		return animais;
	}

	public void criarAnimal(Animal animal) {
		this.animalRep.save(animal);
	}
	
	public void removerAnimal(Integer idAdm, String senhaAdm, Integer idAnimal) throws Exception {
		
		if(this.usuarioRep.existsByIdAndByHashSenha(idAdm, senhaAdm)) {
			
			this.animalRep.deleteById(idAnimal);
		}else {
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
		if(animal.getUsuario() != null) {
			return true;
		}
		return false;
	}
}
