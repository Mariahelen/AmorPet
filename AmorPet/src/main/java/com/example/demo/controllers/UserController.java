package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class UserController {

	@GetMapping("/login-realizado")
	public ModelAndView exibirPerfil() {
		return new ModelAndView("/perfil");
	}
}
