package com.example.demo.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.exception.LoginInvalido;
import com.example.demo.model.Usuario;
import com.example.demo.service.AnimalService;
import com.example.demo.service.UsuarioService;

@Controller
public class MenuController {
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private AnimalService animalService;
	
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
	public ModelAndView login(HttpSession session) {
		ModelAndView mv = new ModelAndView("/login");
		mv.addObject("email", session.getAttribute("email"));
		return mv;
	}
	@PostMapping("/login")
	public String efetuarLogin(@RequestParam(required = true) String email, @RequestParam(required = true) String senha,
			RedirectAttributes ra, HttpSession session) {
		Usuario usuarioLogado;
		try {
			usuarioLogado = this.usuarioService.efetuarLogin(email, senha);
			session.setAttribute("usuarioLogado", usuarioLogado);
		} catch (LoginInvalido e) {
			session.setAttribute("email", email);
			ra.addFlashAttribute("mensagemError", e.getMessage());
			return "redirect:/login";
		}
		return "redirect:/user/perfil";
	}
	
	@GetMapping("/cadastro")
	public ModelAndView cadastro() {
		ModelAndView mv = new ModelAndView("/cadastro");
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	@PostMapping("/cadastro")
	public ModelAndView salvarCad(@Valid Usuario usuario, BindingResult br, RedirectAttributes ra) {
		
		if(br.hasErrors()) {
			return new ModelAndView("/cadastro");
		}
		try {
			ModelAndView mv = new ModelAndView("redirect:/cadastro");
			usuario.setRole("ROLE_USER");
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
		ModelAndView mv = new ModelAndView("/quero-adotar");
		try {
			mv.addObject("listaAnimais", this.animalService.listarAnimais());
		} catch (Exception e) {
			mv.addObject("errorListaAnimais", "Nenhum resultado encontrado");
		}
		return mv;
	}
	@GetMapping("/descricao-animal/{id}")
	public ModelAndView descricaoAnimal(@PathVariable Integer id) {
		
		ModelAndView mv = new ModelAndView("/descricao-animal");
		try {
			mv.addObject("animal", this.animalService.findById(id));
		} catch (Exception e) {
			mv.addObject("error", e.getMessage());
		}
		return mv;
	}
	@GetMapping("/mapa")
	public String mapa() {
		return "/mapa";
	}
}
