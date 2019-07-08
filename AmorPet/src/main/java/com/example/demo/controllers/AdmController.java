package com.example.demo.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Animal;
import com.example.demo.service.AnimalService;

@Controller
@RequestMapping("/adm")
public class AdmController {
	@Autowired
	private AnimalService animalService;

	@GetMapping("/perfil")
	public ModelAndView exibirPerfil() {
		return new ModelAndView("perfil");
	}
	
	@GetMapping("/candidatos")
	public ModelAndView exibirCandidatos() {
		return new ModelAndView("adm/candidatos");
	}
	
	@GetMapping("/cadastro/animal")
	public ModelAndView cadastroAnimal(RedirectAttributes ra) {
		ModelAndView mv = new ModelAndView("/adm/cadanimal");
		mv.addObject("animal", new Animal());
		return mv;
	}
	
	@PostMapping("/cadastro/animal")
	public String cadastroAnimal(@Valid Animal animal, BindingResult br, RedirectAttributes ra) {
		if(br.hasErrors()) {
			ra.addFlashAttribute("error", "Não foi possível cadastrar o animal");
			return "redirect:/adm/cadastro/animal";
		}
		
		try {
			this.animalService.criarAnimal(animal);
			ra.addFlashAttribute("success", "Animal cadastrado com sucesso!");
		}catch(Exception e) {
			ra.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/adm/cadastro/animal";
	}
	
}
