package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Animal;

public interface AnimalDAO extends JpaRepository<Animal, Integer> {
	
}
