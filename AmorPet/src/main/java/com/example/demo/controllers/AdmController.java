package com.example.demo.controllers;

import java.io.File;
import java.io.IOException;

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
			String path = "C:\\Users\\Rodrigo\\Downloads\\douglas_pdfs" + "\\ProjetosdeProgramacao\\Projetos-STS-2"
					+ "\\AmorPet\\src\\main\\resources\\static\\img\\" + animal.getFile().getOriginalFilename();
			File dest = new File(path);
			animal.getFile().transferTo(dest);
			animal.setCaminhoFoto("/img/"+animal.getFile().getOriginalFilename());
			
			this.animalService.criarAnimal(animal);
			
			String caminho = "/img/" + animal.getFile().getOriginalFilename();
			ra.addFlashAttribute("foto", caminho);
			
			ra.addFlashAttribute("success", "Animal cadastrado com sucesso!");
		}catch (IllegalStateException | IOException e) {
			ra.addFlashAttribute("error", "Não foi possível cadastrar o animal");
			System.out.println(e.getMessage());
		}catch(Exception e) {
			ra.addFlashAttribute("error", e.getMessage());
		}
		
		return "redirect:/adm/cadastro/animal";
	}
	
}
