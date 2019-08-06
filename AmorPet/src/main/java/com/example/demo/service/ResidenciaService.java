package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ResidenciaDAO;
import com.example.demo.model.Residencia;

@Service
public class ResidenciaService {
	@Autowired
	private ResidenciaDAO residenciaRep;
	
	public List<Residencia> listar() {
		return residenciaRep.findAll();
	}
}
