package com.example.demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Selecao;

@Repository
public interface SelecaoDAO extends JpaRepository<Selecao, Integer> {

	@Query("select s from Selecao s where id_animal = :id")
	Optional<Selecao> findByIdAnimal(Integer id);
}
