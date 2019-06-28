package com.example.demo.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UsuarioService usuarioService;
	
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
	
	@PostMapping("/salvarCadastro")
	public ModelAndView salvarCad(@Valid Usuario usuario, BindingResult br) {
		ModelAndView mv = new ModelAndView("/cadastro");
		if(br.hasErrors()) {
			mv.addObject("usuario", usuario);
			return mv;
		}
		try {
			this.usuarioService.criarUsuario(usuario);
			mv.addObject("concluidoCadastro", "Cadastro realizado com Sucesso !!");
		} catch (Exception e) {
			mv.addObject("erroCadastro", e.getMessage());
		}
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	
	
	
	
}
