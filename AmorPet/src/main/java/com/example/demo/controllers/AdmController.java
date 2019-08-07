package com.example.demo.controllers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Animal;
import com.example.demo.model.Pergunta;
import com.example.demo.model.Pontuacao;
import com.example.demo.model.Usuario;
import com.example.demo.service.AnimalService;
import com.example.demo.service.PerguntaService;
import com.example.demo.service.ResidenciaService;
import com.example.demo.util.Util;

@Controller
@RequestMapping("/adm")
public class AdmController {
	@Autowired
	private AnimalService animalService;
	@Autowired
	private PerguntaService perguntaService;
	@Autowired
	private ResidenciaService residenciaService;

	@GetMapping({ "/perfil", "/perfil/editar" })
	public ModelAndView exibirPerfil(HttpSession session) {
		ModelAndView mv = new ModelAndView("/perfil");
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		mv.addObject("usuario", usuario);
		return mv;
	}

	@GetMapping("/candidatos")
	public ModelAndView exibirCandidatos() {
		return new ModelAndView("adm/candidatos");
	}

	@GetMapping("/cadastro/animal")
	public ModelAndView cadastroAnimal() {
		ModelAndView mv = new ModelAndView("/adm/cadanimal");
		mv.addObject("animal", new Animal());
		return mv;
	}

	@PostMapping("/cadastro/animal")
	public String cadastroAnimal(@Valid Animal animal, BindingResult br, RedirectAttributes ra) {
		if (br.hasErrors()) {
			ra.addFlashAttribute("error", "Não foi possível cadastrar o animal");
			return "redirect:/adm/cadastro/animal";
		}
		try {
			String path = Util.pegarCaminhoCompletoParaImagemAnimal(animal.getFile().getOriginalFilename(), animal.getTipoAnimal());
			File destino = new File(path);
			animal.getFile().transferTo(destino);
			animal.setCaminhoFoto(Util.pegarCaminhoImagemAnimal(animal.getTipoAnimal()) + animal.getFile().getOriginalFilename());
			animal.setDataRegistro(LocalDate.now());
			this.animalService.criarAnimal(animal);
			ra.addFlashAttribute("sucesso", "Animal cadastrado com sucesso!");
			// pra da o tempo de criar as pastas e mover os arquivos
			Thread.sleep(5000);

		} catch (IllegalStateException | IOException e) {
			ra.addFlashAttribute("error", "Não foi possível cadastrar o animal");
			System.out.println(e.getMessage());
		} catch (Exception e) {
			ra.addFlashAttribute("error", e.getMessage());
			System.out.println(e.getMessage());
		}

		return "redirect:/adm/cadastro/animal";
	}

	@PostMapping("/editar/animal")
	public String editarAnimal(@Valid Animal animal, BindingResult br, RedirectAttributes ra) {
		if (br.hasErrors()) {
			ra.addFlashAttribute("errorEditar", br.getAllErrors());
			return "redirect:/descricao-animal/" + animal.getId_animal();
		}
		try {
			this.animalService.criarAnimal(animal);
			ra.addFlashAttribute("sucessoEditar", "Editado com sucesso");
		} catch (Exception e) {
			ra.addFlashAttribute("errorEditar", e.getMessage());
		}
		return "redirect:/descricao-animal/" + animal.getId_animal();
	}

	@PostMapping("/remover/animal/{idAnimal}")
	public String removerAnimal(@RequestParam String senhaAdm, @PathVariable Integer idAnimal, RedirectAttributes ra,
			HttpSession session) {

		if (senhaAdm.trim().isEmpty() || idAnimal == null) {
			ra.addFlashAttribute("error", "Não foi possível remover");
			return "redirect:/adotar";
		}
		try {
			Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
			this.animalService.removerAnimal(usuario.getId(), senhaAdm, idAnimal);
			ra.addFlashAttribute("sucesso", "Sucesso ao remover");
		} catch (Exception e) {
			ra.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/adotar";

	}

	@GetMapping("/cadastro/pergunta")
	public ModelAndView cadastroPergunta() {
		ModelAndView mv = new ModelAndView("/adm/cadpergunta");
		mv.addObject("pergunta", new Pergunta());
		mv.addObject("pontuacoes", Pontuacao.values());
		mv.addObject("listaResidencias", this.residenciaService.listar());
		return mv;
	}

	@PostMapping("/cadastro/pergunta")
	public String cadastroPergunta(@Valid Pergunta pergunta, BindingResult br,
			RedirectAttributes ra, HttpSession session) {
		if (br.hasErrors()) {
			ra.addFlashAttribute("listError", br.getAllErrors());
			return "redirect:/adm/cadastro/pergunta";
		}
		try {
			pergunta.setUsuarioAdm((Usuario) session.getAttribute("usuarioLogado"));
			pergunta.setDataRegistro(LocalDate.now());
			this.perguntaService.save(pergunta);
			ra.addFlashAttribute("sucesso", "Sucesso ao criar");
		} catch (Exception e) {
			ra.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/adm/cadastro/pergunta";
	}

	@GetMapping("/logout")
	public String logout() {
		return "redirect:/user/logout";
	}
}
