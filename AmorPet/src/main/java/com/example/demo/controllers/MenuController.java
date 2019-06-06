package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MenuController {
	@GetMapping("/home")
	public String index() {
		return "index";
	}
	@GetMapping("/termos")
	public String termos() {
		return "termos";
	}
	@GetMapping("/navegacao")
	public String navegacao() {
		return "navegacao";
	}
	@GetMapping("/quem-somos")
	public String quemSomos() {
		return "quemsomos";
	}
	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}
	@GetMapping("/cadastro")
	public ModelAndView cadastro() {
		return new ModelAndView("cadastro");
	}
	@GetMapping("/adotar")
	public ModelAndView adotar() {
		return new ModelAndView("quero-adotar");
	}
	@GetMapping("/descricao-animal")
	public String descricaoAnimal() {
		return "descricao-animal";
	}
	
}
