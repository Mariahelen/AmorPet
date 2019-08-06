package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Residencia;

@Repository
public interface ResidenciaDAO extends JpaRepository<Residencia, Integer> {

}
