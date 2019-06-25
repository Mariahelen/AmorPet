package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

	@GetMapping({"/perfil", "/perfil/editar"})
	public ModelAndView exibirPerfil() {
		return new ModelAndView("/perfil");
	}
	
	@GetMapping("/quero-adotar/parte-1")
	public ModelAndView exibirFormUm() {
		return new ModelAndView("/user/form-etapa-1");
	}
	
	@GetMapping("/quero-adotar/parte-2")
	public ModelAndView exibirFormDois() {
		return new ModelAndView("/user/form-etapa-2");
	}
}
