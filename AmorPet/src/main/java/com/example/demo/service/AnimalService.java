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
		return Optional
				.ofNullable(this.animalRep.findAll(Sort.by("nome")))
				.orElseThrow(Exception::new);
	}

	public void criarAnimal(Animal animal) {
		this.animalRep.save(animal);
	}
}
