package com.example.demo.service;

import java.util.List;

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
	
	public void removerAnimal(Integer id, String senhaAdm, Integer idAnimal) throws Exception {
		if(this.usuarioRep.existsByIdAndByHashSenha(id, senhaAdm)) {
			this.animalRep.deleteById(idAnimal);
		}else {
			throw new Exception("Não foi possivel remover");
		}
		
	}
}
