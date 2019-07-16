package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AnimalDAO;
import com.example.demo.model.Animal;

@Service
public class AnimalService {
	@Autowired
	private AnimalDAO animalRep;

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
}
