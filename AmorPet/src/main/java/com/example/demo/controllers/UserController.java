package com.example.demo.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.exception.IdadeInvalidaException;
import com.example.demo.model.Animal;
import com.example.demo.model.Endereco;
import com.example.demo.model.Processo;
import com.example.demo.model.Residencia;
import com.example.demo.model.Resposta;
import com.example.demo.model.Usuario;
import com.example.demo.service.AnimalService;
import com.example.demo.service.PerguntaService;
import com.example.demo.service.ProcessoService;
import com.example.demo.service.ResidenciaService;
import com.example.demo.service.RespostaService;
import com.example.demo.service.SelecaoService;
import com.example.demo.service.UsuarioService;
import com.example.demo.util.Util;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private ResidenciaService residenciaService;
	@Autowired
	private SelecaoService selecaoService;
	@Autowired
	private PerguntaService perguntaService;
	@Autowired
	private ProcessoService processoService;
	@Autowired
	private RespostaService respostaService;
	@Autowired
	private AnimalService animalService;

	@GetMapping({ "/perfil", "/perfil/editar" })
	public ModelAndView exibirPerfil(HttpSession session) {
		ModelAndView mv = new ModelAndView("/perfil");
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		
		if(usuario.getEndereco() == null) {
			usuario.setEndereco(new Endereco());
		}
		if(usuario.getEndereco().getResidencia() == null) {
			usuario.getEndereco().setResidencia(new Residencia());
		}
		if(usuario.getAnimais() == null) {
			usuario.setAnimais(new ArrayList<Animal>());
		}
		
		mv.addObject("usuario", usuario);
		mv.addObject("listaResidencias", this.residenciaService.listar());
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
					Util.apagarFotoAntiga(usuario.getCaminhoFoto());
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
			
		}catch(IdadeInvalidaException e) {
			ra.addFlashAttribute("errorIdade", e.getMessage());
			
		}catch (Exception e) {
			ra.addFlashAttribute("error", "Não foi possível alterar");
			System.out.println(e.getMessage());
		}
		return "redirect:/user/perfil/editar";
	}
	
	@GetMapping("/historico")
	public String exibirHistorico() {
		return "/user/historico";
	}
	
	@GetMapping("/status")
	public ModelAndView exibirStatus(HttpSession session) {
		ModelAndView mv = new ModelAndView("/user/status");
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		try {
			List<Processo> processos = this.processoService.lista(usuario);
			mv.addObject("listaProcessos", processos);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return mv;
	}

	@GetMapping("/quero-adotar/{idAnimal}")
	public String iniciarSelecaoAnimal(@PathVariable Integer idAnimal, RedirectAttributes ra, HttpSession session) {

		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		Processo processo = this.processoService.criarProcesso(usuario);
		try {
			Animal animal = this.animalService.findById(idAnimal);
			if (!this.animalService.verificarSeDisponivel(animal, usuario)) {
				return "redirect:/descricao-animal/" + idAnimal;
			}
			// verifica se existe uma selecao aberta pra este animal
			// verifica se este processo já está nesta selecao
			this.selecaoService.selecaoAnimal(animal, processo);
		} catch (Exception e) {
			ra.addFlashAttribute("error", "Animal não encontrado");
			System.out.println(e.getMessage());
			return "redirect:/adotar";
		}

		if (this.usuarioService.verificarEndereco(usuario.getEndereco())) {
			return "redirect:/user/quero-adotar/" + idAnimal + "/cadastro/endereco";
		}
		return "redirect:/user/quero-adotar/" + idAnimal + "/etapa/1";
	}

	@GetMapping("/quero-adotar/{idAnimal}/cadastro/endereco")
	public ModelAndView exibirFormEndereco(@PathVariable Integer idAnimal, HttpSession session) {
		ModelAndView mv = new ModelAndView("/user/form-endereco");
		// pega o usuario da sessao
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		/* bloco try destinado a verificar se o animal está disponivel */
		try {
			Animal animal = this.animalService.findById(idAnimal);
			if (!this.animalService.verificarSeDisponivel(animal, usuario)) {
				return new ModelAndView("redirect:/descricao-animal/" + idAnimal);
			}
		} catch (Exception e) {
			return new ModelAndView("redirect:/adotar");
		}
		mv.addObject("endereco", usuario.getEndereco());
		mv.addObject("idAnimal", idAnimal);
		mv.addObject("listaResidencias", this.residenciaService.listar());
		return mv;
	}

	@PostMapping("/quero-adotar/{idAnimal}/cadastro/endereco")
	public ModelAndView salvarFormEndereco(@Valid Endereco endereco, BindingResult br, @PathVariable Integer idAnimal,
			HttpSession session) {

		ModelAndView mv = new ModelAndView("/user/form-endereco");
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		/* bloco try destinado a verificar se o animal está disponivel */
		try {
			Animal animal = this.animalService.findById(idAnimal);
			if (!this.animalService.verificarSeDisponivel(animal, usuario)) {
				return new ModelAndView("redirect:/descricao-animal/" + idAnimal);
			}
		} catch (Exception e) {
			return new ModelAndView("redirect:/adotar");
		}

		if (endereco.getResidencia().getIdResidencia() == null) {
			mv.addObject("erroResidencia", "Residencia é necessário");
		}
		if (br.hasErrors()) {
			mv.addObject("idAnimal", idAnimal);
			mv.addObject("listaResidencias", this.residenciaService.listar());
			return mv;
		}
		try {
			Residencia residencia = this.residenciaService
					.obterResidenciasDisponiveis(endereco.getResidencia().getIdResidencia());
			endereco.setResidencia(residencia);

			// seta endereco do usuario e salva
			usuario.setEndereco(endereco);
			this.usuarioService.save(usuario);
		} catch (Exception e) {
			mv.setViewName("/user/form-endereco");
			mv.addObject("idAnimal", idAnimal);
			mv.addObject("listaResidencias", this.residenciaService.listar());
			mv.addObject("error", "Erro ao realizar o cadastro, tente novamente");
			System.out.println(e.getMessage());
			return mv;
		}
		return new ModelAndView("redirect:/user/quero-adotar/" + idAnimal + "/etapa/1");
	}

	@GetMapping("/quero-adotar/{idAnimal}/etapa/1")
	public ModelAndView exibirEtapaUm(@PathVariable Integer idAnimal, HttpSession session) {
		ModelAndView mv = new ModelAndView("/user/form-etapa-1");
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		/* bloco try destinado a verificar se o animal está disponivel */
		try {
			Animal animal = this.animalService.findById(idAnimal);
			if (!this.animalService.verificarSeDisponivel(animal, usuario)) {
				return new ModelAndView("redirect:/descricao-animal/" + idAnimal);
			}
		} catch (Exception e) {
			return new ModelAndView("redirect:/adotar");
		}
		mv.addObject("idAnimal", idAnimal);
		// respostas relacionadas com as perguntas
		List<Resposta> respostas = this.respostaService.criarListaRespostas(this.perguntaService.listar(), usuario,
				idAnimal);
		mv.addObject("listaRespostas", respostas);
		return mv;
	}

	@PostMapping("/quero-adotar/{idAnimal}/etapa/1")
	@ResponseStatus(HttpStatus.CREATED)
	public String exibirEtapaUm(@RequestBody Resposta[] respostasUsuario, RedirectAttributes ra,
			@PathVariable Integer idAnimal, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		Animal animal;
		/* bloco try destinado a verificar se o animal está disponivel */
		try {
			animal = this.animalService.findById(idAnimal);
			if (!this.animalService.verificarSeDisponivel(animal, usuario)) {
				return "redirect:/descricao-animal/" + idAnimal;
			}
		} catch (Exception e) {
			return "redirect:/adotar";
		}

		try {
			List<Resposta> respostas = this.respostaService.criarListaRespostasUsuario(respostasUsuario, usuario,
					animal);
			this.processoService.salvarRespostasAndProcesso(usuario, animal, respostas);
		} catch (Exception e) {
			ra.addFlashAttribute("error", "Erro ao enviar, tente novamente");
			System.out.println(e.getMessage());
			return "redirect:/user/quero-adotar/" + idAnimal + "/etapa/1";
		}
		return "redirect:/user/quero-adotar/" + idAnimal + "/etapa/2";
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
