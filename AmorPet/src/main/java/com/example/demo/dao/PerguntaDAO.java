package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Pergunta;

@Repository
public interface PerguntaDAO extends JpaRepository<Pergunta, Integer> {

}
