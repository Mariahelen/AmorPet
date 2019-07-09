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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Contato;
import com.example.demo.model.DadosPessoais;
import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping({ "/perfil", "/perfil/editar" })
	public ModelAndView exibirPerfil(HttpSession session, RedirectAttributes ra) {
		ModelAndView mv = new ModelAndView("/perfil");
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		mv.addObject("dadosPessoais", usuario.getDadosPessoais());
		mv.addObject("contato", usuario.getContato());
		mv.addObject("endereco", usuario.getEndereco());
		return mv;
	}

	// ALTERAR DADOS PESSOAIS
	@PostMapping("/perfil/editar/dadosPessoais")
	public String alterarDadosPessoais(@Valid DadosPessoais dp, BindingResult br, RedirectAttributes ra) {

		if (br.hasErrors()) {
			ra.addFlashAttribute("error", "Não foi possível alterar");
			return "redirect:/user/perfil/editar";
		}
		try {
			this.usuarioService.salvarDadosPessoais(dp);
			ra.addFlashAttribute("sucesso", "Alteração Feita com Sucesso!");

		} catch (Exception e) {
			ra.addFlashAttribute("error", "Não foi possível alterar");
		}
		return "redirect:/user/perfil/editar";
	}

	// ALTERAR TELEFONE
	@PostMapping("/perfil/editar/telefone")
	public String alterarTelefone(@RequestParam String telefone, RedirectAttributes ra) {

		try {
			this.usuarioService.salvarTelefone(telefone);
			ra.addFlashAttribute("sucesso", "Alteração Feita com Sucesso!");

		} catch (Exception e) {
			ra.addFlashAttribute("error", "Não foi possível alterar");
		}
		return "redirect:/user/perfil/editar";
	}

	// ALTERAR CONTATO
	@PostMapping("/perfil/editar/contato")
	public String alterarContato(@Valid Contato c, BindingResult br, RedirectAttributes ra) {

		if (br.hasErrors()) {
			ra.addFlashAttribute("error", "Não foi possível alterar");
			return "redirect:/user/perfil/editar";
		}

		try {
			this.usuarioService.salvarContato(c);
			ra.addFlashAttribute("sucesso", "Alteração Feita com Sucesso!");

		} catch (Exception e) {

			ra.addFlashAttribute("error", "Não foi possível alterar");
		}

		return "redirect:/user/perfil/editar";
	}

	/*
	 * @PostMapping("/perfil/editar") public String editarPerfil(@Valid Usuario
	 * usuario, BindingResult br, RedirectAttributes ra) { if(br.hasErrors()) {
	 * ModelAndView mv = new ModelAndView("/perfil"); // syso para visualizar como o
	 * objeto esta sendo enviado System.out.println(usuario.toString()); return mv;
	 * } try { this.usuarioService.editarUsuario(usuario);
	 * ra.addFlashAttribute("msgSuccess", "Alteração feita com sucesso!");
	 * }catch(Exception e) { ra.addFlashAttribute("errors", e.getMessage()); }
	 * return new ModelAndView("redirect:/user/perfil/editar"); }
	 */

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
