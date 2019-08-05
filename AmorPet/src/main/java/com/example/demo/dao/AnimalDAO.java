package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Animal;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalDAO extends JpaRepository<Animal, Integer> {
	
}
