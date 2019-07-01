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

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping({"/perfil", "/perfil/editar"})
	public ModelAndView exibirPerfil(HttpSession session, RedirectAttributes ra) {
		ModelAndView mv = new ModelAndView("/perfil");
		if(ra.containsAttribute("errors")) {
			mv.addObject("errors", ra.getFlashAttributes().get("errors"));
		}
		mv.addObject("usuario", session.getAttribute("usuarioLogado"));
		return mv;
	}
	@PostMapping("/perfil/editar")
	public ModelAndView editarPerfil(@Valid Usuario usuario, 
						BindingResult br, RedirectAttributes ra) {
		if(br.hasErrors()) {
			ra.addFlashAttribute("errors", br.getFieldErrors());
			return new ModelAndView("/perfil");
		}
		try {
			this.usuarioService.editarUsuario(usuario);
			ra.addFlashAttribute("msgSuccess", "Alteração feita com sucesso!");
		}catch(Exception e) {
			ra.addFlashAttribute("errors", e.getMessage());
		}
		return new ModelAndView("redirect:/user/perfil/editar");
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
