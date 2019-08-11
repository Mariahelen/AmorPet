package com.example.demo.service;

import java.util.List;
import java.util.Optional;

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
	
	public Residencia obterResidenciasDisponiveis(Integer id) throws Exception {
		Optional<Residencia> residencia = this.residenciaRep.findById(id);
		if(residencia.isPresent()) {
			if(! residencia.get().getTipoResidencia().equalsIgnoreCase("Todos")) {
				return residencia.get();
			}
		}
		throw new Exception();
	}
}
