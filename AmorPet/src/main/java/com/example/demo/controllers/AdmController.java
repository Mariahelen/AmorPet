package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/adm")
public class AdmController {

	@GetMapping("/perfil")
	public ModelAndView exibirPerfil() {
		return new ModelAndView("perfil");
	}
	
	@GetMapping("/candidatos")
	public ModelAndView exibirCandidatos() {
		return new ModelAndView("adm/candidatos");
	}
}
