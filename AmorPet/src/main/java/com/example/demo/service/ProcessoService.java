package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ProcessoDAO;

@Service
public class ProcessoService {
	@Autowired
	private ProcessoDAO processoRep;
	
}
