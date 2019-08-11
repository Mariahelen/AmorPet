package com.example.demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Selecao;

@Repository
public interface SelecaoDAO extends JpaRepository<Selecao, Integer> {

	@Query("select s from Selecao s where s.idAnimal = (select idAnimal from Animal a where idAnimal = :idAnimal)")
	Optional<Selecao> findByIdAnimal(Integer idAnimal);
	
//	select * from processo where id_selecao in (select id_selecao from selecao where idAnimal = 2) AND id_usuario = 2
//	@Query("select p from Processo p where p.idUsuario = :idUsuario AND p.idSelecao = (select s.idSelecao from Selecao s where s.idAnimal = :idAnimal AND s.aberta = true)")
//	Optional<Processo> findByProcesso(Integer idUsuario, Integer idAnimal);
}
