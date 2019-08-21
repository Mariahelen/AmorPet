package com.example.demo.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Animal;

@Repository
public interface AnimalDAO extends JpaRepository<Animal, Integer> {

	@Query("select a from Animal a where a.usuario is null")
	List<Animal> findAllByDisponiveis(Sort sort);
}
