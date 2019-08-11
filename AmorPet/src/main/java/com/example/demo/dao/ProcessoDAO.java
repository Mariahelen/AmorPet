package com.example.demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Processo;

@Repository
public interface ProcessoDAO extends JpaRepository<Processo, Integer> {

	@Query("select p from Processo p where id_usuario = :idUsuario")
	Optional<Processo> findByIdUsuario(Integer idUsuario);
}
