package com.example.demo.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.exception.LoginInvalido;
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
	
	@GetMapping({"/login"})
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("/index");
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	
	@PostMapping("/efetuaLogin")
	public String efetuarLogin(Usuario usuario, RedirectAttributes ra, HttpSession session) {
		Usuario usuarioLogado;
		try {
			usuarioLogado = this.usuarioService.efetuarLogin(usuario.getEmail(), usuario.getConfirmaSenha());
			session.setAttribute("usuarioLogado", usuarioLogado);
		} catch (LoginInvalido e) {
			ra.addFlashAttribute("mensagemErro", e.getMessage());
			return "redirect:/";
		}
		return "redirect:/user/perfil";
	}
	
	@PostMapping("/salvarCadastro")
	public ModelAndView salvarCad(@Valid Usuario usuario, BindingResult br) {
		ModelAndView mv = new ModelAndView("/cadastro");
		if(br.hasErrors()) {
			mv.addObject("usuario", usuario);
			System.out.println(usuario.toString());
			return mv;
		}
		try {
			this.usuarioService.criarUsuario(usuario);
		}catch(Exception e) {
			mv.addObject("mensagemError", e.getMessage());
			mv.addObject("usuario", usuario);
		}
		return mv;
	}
	
}
