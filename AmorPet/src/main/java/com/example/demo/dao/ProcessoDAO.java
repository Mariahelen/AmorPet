package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Animal;
import com.example.demo.model.Processo;
import com.example.demo.model.Selecao;
import com.example.demo.model.Usuario;

@Repository
public interface ProcessoDAO extends JpaRepository<Processo, Integer> {

	@Query("select p from Processo p where id_usuario = :idUsuario")
	Optional<Processo> findByIdUsuario(Integer idUsuario);
	
	@Query("select p from Processo p where p.idUsuario = :usuario and p.idSelecao = "
			+ "(select s.idSelecao from Selecao s where s.idAnimal = :animal AND aberta = true)")
	Optional<Processo> findByUsuarioByAnimal(Usuario usuario, Animal animal);

	@Query("select p from Processo p where p.idSelecao = :selecao order by p.pontuacaoFinal desc")
	List<Processo> findAllBySelecao(Selecao selecao);
}
