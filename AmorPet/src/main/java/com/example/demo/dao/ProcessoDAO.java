package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Processo;

@Repository
public interface ProcessoDAO extends JpaRepository<Processo, Integer> {

}
