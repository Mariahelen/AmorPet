package com.example.demo.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

//import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Estado;
import com.example.demo.model.Usuario;
import com.example.demo.service.ResidenciaService;
import com.example.demo.service.UsuarioService;
import com.example.demo.util.Util;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private ResidenciaService residenciaService;

	@GetMapping({ "/perfil", "/perfil/editar" })
	public ModelAndView exibirPerfil(HttpSession session) {
		ModelAndView mv = new ModelAndView("/perfil");
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		mv.addObject("usuario", usuario);
		return mv;
	}

	@PostMapping("/perfil")
	public String editarFoto(@RequestParam MultipartFile file, RedirectAttributes ra, HttpSession session) {
		if (!file.isEmpty()) {
			if (file.getOriginalFilename().endsWith(".png") || file.getOriginalFilename().endsWith(".jpg")
					|| file.getOriginalFilename().endsWith(".jpeg")) {

				try {
					String path = Util.caminhoParaImagemUsuario(file.getOriginalFilename());
					File destino = new File(path);
					Usuario usuarioSessao = (Usuario) session.getAttribute("usuarioLogado");
					// pra evitar a exception do metodo editarPerfil
					usuarioSessao.setConfirmaSenha(usuarioSessao.getHashSenha());
					Usuario usuario = this.usuarioService.editarPerfil(usuarioSessao);
					// usar uma criptografia para nao haver conflitos no nome da foto
					usuario.setCaminhoFoto("/img/usuarios/" + file.getOriginalFilename());
					this.usuarioService.save(usuario);
					file.transferTo(destino);
					session.setAttribute("usuarioLogado", usuario);
					// pra sincronizar com o refresh do sts
					Thread.sleep(5000);

				} catch (IllegalStateException | IOException e) {
					ra.addFlashAttribute("errorSalvarFoto", "Não foi possível adicionar a foto, tente novamente");
					System.out.println(e.getMessage());
				} catch (Exception e) {
					ra.addFlashAttribute("errorSalvarFoto", "Não foi possível adicionar a foto, tente novamente");
				}
			} else {
				ra.addFlashAttribute("errorSalvarFoto",
						"Erro ao adicionar a foto, verifique o tipo de extensão do arquivo");
			}
		} else {
			ra.addFlashAttribute("errorSalvarFoto",
					"Erro ao adicionar a foto, verifique o tipo de extensão do arquivo");
		}
		return "redirect:/user/perfil";
	}

	@PostMapping("/perfil/editar")
	public String editarPerfil(@ModelAttribute Usuario usuario, RedirectAttributes ra, HttpSession session) {
		List<String> errors = Util.validaUsuario(usuario);
		if (errors != null) {
			ra.addFlashAttribute("listaErrors", errors);
			return "redirect:/user/perfil/editar";
		}
		try {
			Usuario usuarioSessao = (Usuario) session.getAttribute("usuarioLogado");
			usuario.setId(usuarioSessao.getId());
			usuario = this.usuarioService.editarPerfil(usuario);
			this.usuarioService.save(usuario);
			session.setAttribute("usuarioLogado", usuario);
			ra.addFlashAttribute("sucesso", "Alteração realizada com sucesso");
		} catch (Exception e) {
			ra.addFlashAttribute("error", "Não foi possível alterar");
		}
		return "redirect:/user/perfil/editar";
	}

	@GetMapping("/quero-adotar/parte-1")
	public ModelAndView exibirFormUm() {
		ModelAndView mv = new ModelAndView("/user/form-etapa-1");
		mv.addObject("estados", Estado.values());
		mv.addObject("listaResidencias", this.residenciaService.listar());
		return mv;
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

	@PostMapping("/desativarConta")
	public String desativar(@RequestParam String hashSenha, @RequestParam String confirmaSenha, RedirectAttributes ra,
			HttpSession session) {
		if (!hashSenha.equals(confirmaSenha) || hashSenha.isEmpty() || confirmaSenha.isEmpty()) {
			ra.addFlashAttribute("messagemError", "As senhas não coincidem");
			return "redirect:/user/perfil/editar";
		} else {
			Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
			usuario.setAtivo(false);
			this.usuarioService.save(usuario);
			session.setAttribute("usuarioLogado", usuario);
			ra.addFlashAttribute("messagemSucesso", "Conta desativada com sucesso");
		}

		return "redirect:/user/logout";
	}
	

}
