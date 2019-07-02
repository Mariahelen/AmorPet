package com.example.demo.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.exception.LoginInvalido;
import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;

@Controller
public class MenuController {
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/home")
	public String index() {
		return "/index";
	}
	@GetMapping("/termos")
	public String termos() {
		return "/termos";
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
	public ModelAndView login(RedirectAttributes ra) {
		ModelAndView mv = new ModelAndView("/login");
		if(ra.containsAttribute("mensagemError")) {
			mv.addObject("mensagemSuccess", ra.getFlashAttributes().get("mensagemSuccess"));
		}
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	@PostMapping("/login")
	public String efetuarLogin(Usuario usuario, RedirectAttributes ra, HttpSession session) {
		Usuario usuarioLogado;
		try {
			usuarioLogado = this.usuarioService.efetuarLogin(usuario.getEmail(), usuario.getHashSenha());
			session.setAttribute("usuarioLogado", usuarioLogado);
		} catch (LoginInvalido e) {
			ra.addFlashAttribute("mensagemError", e.getMessage());
			return "redirect:/login";
		}
		return "redirect:/user/perfil";
	}
	
	@GetMapping("/cadastro")
	public ModelAndView cadastro(RedirectAttributes ra) {
		ModelAndView mv = new ModelAndView("/cadastro");
		if(ra.containsAttribute("mensagemSuccess")) {
			mv.addObject("mensagemSuccess", ra.getFlashAttributes().get("mensagemSuccess"));
		}
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	@PostMapping("/cadastro")
	public ModelAndView salvarCad(@Valid Usuario usuario, BindingResult br, RedirectAttributes ra) {
		
		if(br.hasErrors()) {
			ModelAndView mv = new ModelAndView("/cadastro");
			return mv;
		}
		try {
			ModelAndView mv = new ModelAndView("redirect:/cadastro");
			this.usuarioService.criarUsuario(usuario);
			ra.addFlashAttribute("mensagemSuccess", "Conta criada com sucesso!");
			return mv;
		}catch(Exception e) {
			ModelAndView mv = new ModelAndView("/cadastro");
			mv.addObject("mensagemError", e.getMessage());
			mv.addObject("usuario", usuario);
			return mv;
		}
	}
	
	@GetMapping("/adotar")
	public ModelAndView adotar() {
		return new ModelAndView("/quero-adotar");
	}
	@GetMapping("/descricao-animal")
	public String descricaoAnimal() {
		return "/descricao-animal";
	}
	@GetMapping("/mapa")
	public String mapa() {
		return "/mapa";
	}
}
