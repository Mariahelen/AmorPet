package com.example.demo.controllers;

//import javax.servlet.http.HttpServletRequest;
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

	@GetMapping({ "/perfil", "/perfil/editar" })
	public ModelAndView exibirPerfil(HttpSession session) {
		ModelAndView mv = new ModelAndView("/perfil");
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		mv.addObject("usuario", usuario);
		return mv;
	}

	@PostMapping("/perfil/editar")
	public String editarPerfil(@Valid Usuario usuario, BindingResult br, RedirectAttributes ra,
			HttpSession session) {
		if(br.hasErrors()) {
			ra.addFlashAttribute("listaErrors", br.getAllErrors());
			return "redirect:/user/perfil/editar";
		}
		try {
			Usuario usuarioSessao = (Usuario) session.getAttribute("usuarioLogado");
			Usuario usuarioDoBanco = this.usuarioService.findById(usuarioSessao.getId());
			usuarioDoBanco = this.usuarioService.editarPerfil(usuario);
			this.usuarioService.save(usuarioDoBanco);
			session.setAttribute("usuarioLogado", usuarioDoBanco);
			ra.addFlashAttribute("sucesso", "Alteração realizada com sucesso");
		}catch(Exception e) {
			ra.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/user/perfil/editar";
	}

	@GetMapping("/quero-adotar/parte-1")
	public ModelAndView exibirFormUm() {
		return new ModelAndView("/user/form-etapa-1");
	}

	@GetMapping("/quero-adotar/parte-2")
	public ModelAndView exibirFormDois() {
		return new ModelAndView("/user/form-etapa-2");
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/home";
	}

}
