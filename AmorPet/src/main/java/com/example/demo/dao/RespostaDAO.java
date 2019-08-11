package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Resposta;

@Repository
public interface RespostaDAO extends JpaRepository<Resposta, Integer> {

}
