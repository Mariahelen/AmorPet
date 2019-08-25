package com.example.demo.controllers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.exception.IdadeInvalidaException;
import com.example.demo.model.Animal;
import com.example.demo.model.Avaliacao;
import com.example.demo.model.Pergunta;
import com.example.demo.model.Pontuacao;
import com.example.demo.model.Processo;
import com.example.demo.model.Resposta;
import com.example.demo.model.Selecao;
import com.example.demo.model.Usuario;
import com.example.demo.service.AnimalService;
import com.example.demo.service.PerguntaService;
import com.example.demo.service.ProcessoService;
import com.example.demo.service.ResidenciaService;
import com.example.demo.service.RespostaService;
import com.example.demo.service.SelecaoService;
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
	@Autowired
	private SelecaoService selecaoService;
	@Autowired
	private ProcessoService processoService;
	@Autowired
	private RespostaService respostaService;

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
			if (animal.getCaminhoFoto().trim().isEmpty() && animal.getCaminhoFoto() != null) {
				String path = Util.pegarCaminhoCompletoParaImagemAnimal(animal.getFile().getOriginalFilename(),
						animal.getTipoAnimal());
				File destino = new File(path);
				animal.getFile().transferTo(destino);
				animal.setCaminhoFoto(
						Util.pegarCaminhoImagemAnimal(animal.getTipoAnimal()) + animal.getFile().getOriginalFilename());
			} else {
				animal.setCaminhoFoto(null);
			}
			animal.setDataRegistro(LocalDate.now());
			this.animalService.criarAnimal(animal);
			ra.addFlashAttribute("sucesso", "Animal cadastrado com sucesso!");
			// pra da o tempo de criar as pastas e mover os arquivos
			Thread.sleep(5000);

		} catch (IllegalStateException | IOException e) {
			ra.addFlashAttribute("error", "Não foi possível cadastrar o animal");
			System.out.println(e.getMessage());
		}catch(IdadeInvalidaException e) {
			ra.addFlashAttribute("errorIdade", e.getMessage());
		}catch (Exception e) {
			ra.addFlashAttribute("error", e.getMessage());
			System.out.println(e.getMessage());
		}

		return "redirect:/adm/cadastro/animal";
	}

	@PostMapping("/editar/animal")
	public String editarAnimal(@Valid Animal animal, BindingResult br, RedirectAttributes ra) {
		if (br.hasErrors()) {
			ra.addFlashAttribute("errorEditar", br.getAllErrors());
			return "redirect:/descricao-animal/" + animal.getIdAnimal();
		}
		try {
			// apaga foto antiga
			Util.apagarFotoAntiga(animal.getCaminhoFoto());
			if (animal.getCaminhoFoto().trim().isEmpty() && animal.getCaminhoFoto() != null) {
				String path = Util.pegarCaminhoCompletoParaImagemAnimal(animal.getFile().getOriginalFilename(),
						animal.getTipoAnimal());
				File destino = new File(path);
				animal.getFile().transferTo(destino);
				animal.setCaminhoFoto(
						Util.pegarCaminhoImagemAnimal(animal.getTipoAnimal()) + animal.getFile().getOriginalFilename());
			} else {
				animal.setCaminhoFoto(null);
			}
			animal.setDataRegistro(LocalDate.now());
			this.animalService.criarAnimal(animal);
			ra.addFlashAttribute("sucesso", "Animal Editado com sucesso!");
			// pra da o tempo de criar as pastas e mover os arquivos
			Thread.sleep(5000);

		} catch (IllegalStateException | IOException e) {
			ra.addFlashAttribute("error", "Não foi possível editar o animal");
			System.out.println(e.getMessage());
		}catch(IdadeInvalidaException e) {
			ra.addFlashAttribute("errorIdade", e.getMessage());
		}catch (Exception e) {
			ra.addFlashAttribute("error", e.getMessage());
			System.out.println(e.getMessage());
		}
		return "redirect:/descricao-animal/" + animal.getIdAnimal();
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
	public String cadastroPergunta(@Valid Pergunta pergunta, BindingResult br, RedirectAttributes ra,
			HttpSession session) {
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

	@GetMapping("/selecoes")
	public ModelAndView exibirListaSelecao() {
		ModelAndView mv = new ModelAndView("/adm/lista-selecoes");
		mv.addObject("listaSelecoes", this.selecaoService.lista());
		return mv;
	}

	@GetMapping("/selecoes/{idSelecao}/processos")
	public ModelAndView exibirSelecao(@PathVariable Integer idSelecao) {
		ModelAndView mv = new ModelAndView("/adm/selecao");
		try {
			Selecao selecao = this.selecaoService.findById(idSelecao);
			selecao = this.processoService.listarProcessos(selecao);
			mv.addObject("selecao", selecao);
			mv.addObject("finalizado", this.selecaoService.verificarProcessosAptos(selecao));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			mv.setViewName("redirec:/adm/selecoes");
		}
		return mv;
	}

	@DeleteMapping("/selecoes/{idSelecao}/processos/{idProcesso}")
	public String removerProcesso(@PathVariable Integer idSelecao, @PathVariable Integer idProcesso,
			RedirectAttributes ra) {
		try {
			this.processoService.removerProcesso(idSelecao, idProcesso);
		} catch (Exception e) {
//			System.out.println(e.getMessage());
			return "redirect:/adm/selecoes";
		}
		return "redirect:/adm/selecoes/" + idSelecao + "/processos";
	}

	@GetMapping("/selecoes/{idSelecao}/processos/{idProcesso}/respostas")
	public ModelAndView exibirRespostas(@PathVariable Integer idSelecao, @PathVariable Integer idProcesso) {
		ModelAndView mv = new ModelAndView("/adm/form-etapa-2");
		try {
			Selecao selecao = this.selecaoService.findById(idSelecao);
			this.selecaoService.verificarPosicaoProcesso(selecao, idProcesso);
			mv.addObject("idSelecao", idSelecao);
			mv.addObject("idProcesso", idProcesso);
			mv.addObject("listaRespostas", this.selecaoService.buscaResposta(selecao, idProcesso));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ModelAndView("redirect:/adm/selecoes/" + idSelecao + "/processos");
		}
		return mv;
	}

	@PostMapping("/selecoes/{idSelecao}/processos/{idProcesso}/respostas")
	@ResponseStatus(HttpStatus.CREATED)
	public String salvarRespostas(@RequestBody Resposta[] respostas, @PathVariable Integer idSelecao,
			@PathVariable Integer idProcesso) {

		try {
			Selecao selecao = this.selecaoService.findById(idSelecao);
			this.selecaoService.verificarPosicaoProcesso(selecao, idProcesso);
			Processo processo = this.processoService.findById(idProcesso);
			List<Resposta> listaRespostas = this.respostaService.criarListaConfirmacoesUsuario(respostas,
					processo.getIdUsuario(), selecao.getIdAnimal());
			this.processoService.salvarRespostasAndProcesso(processo.getIdUsuario(), selecao.getIdAnimal(),
					listaRespostas);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "redirect:/adm/selecoes/" + idSelecao + "/processos";
		}
		return "redirect:/adm/selecoes/" + idSelecao + "/processos/" + idProcesso + "/respostas";
	}

	@GetMapping("/selecoes/{idSelecao}/processos/{idProcesso}/avaliacao")
	public ModelAndView exibirAvaliacao(@PathVariable Integer idSelecao, @PathVariable Integer idProcesso) {
		ModelAndView mv = new ModelAndView("/adm/avaliacao");
		try {
			Selecao selecao = this.selecaoService.findById(idSelecao);
			this.selecaoService.verificarPosicaoProcesso(selecao, idProcesso);
			mv.addObject("idSelecao", idSelecao);
			mv.addObject("idProcesso", idProcesso);
			mv.addObject("avaliacao", new Avaliacao());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ModelAndView("redirect:/adm/selecoes/" + idSelecao + "/processos");
		}
		return mv;
	}

	@PostMapping("/selecoes/{idSelecao}/processos/{idProcesso}/avaliacao")
	public String salvarAvaliacao(@PathVariable Integer idSelecao, @PathVariable Integer idProcesso,
			@RequestParam(required = false) List<String> respostaPet, @RequestParam(required = false) List<String> respostaDono) {
		try {
			Selecao selecao = this.selecaoService.findById(idSelecao);
			this.selecaoService.verificarPosicaoProcesso(selecao, idProcesso);
			this.selecaoService.verificarRespostasAvaliacao(respostaPet, respostaDono);
			this.processoService.salvarAvaliacaoAndProcesso(respostaPet, respostaDono, idProcesso);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return "redirect:/adm/selecoes/" + idSelecao + "/processos/";
		}
		return "redirect:/adm/selecoes/" + idSelecao + "/processos/" + idProcesso + "/avaliacao";
	}

	@GetMapping("/selecoes/{idSelecao}/processos/iniciar/etapa/{etapa}")
	public String iniciarProximaEtapa(@PathVariable Integer idSelecao, @PathVariable Integer etapa,
			RedirectAttributes ra) {
		try {
			this.selecaoService.iniciarProximaEtapa(idSelecao, etapa);
		} catch (Exception e) {
			ra.addFlashAttribute("error", "Ainda há participantes que não foram verificados");
			System.out.println(e.getMessage());
		}
		return "redirect:/adm/selecoes/" + idSelecao + "/processos";
	}

	@GetMapping("/selecoes/{idSelecao}/processos/concluir")
	public String concluirSelecao(@PathVariable Integer idSelecao) {
		try {
			Selecao selecao = this.selecaoService.findById(idSelecao);
			this.selecaoService.concluirSelecao(selecao);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/adm/selecoes/" + idSelecao + "/processos";
	}

	@GetMapping("/selecoes/{idSelecao}/processos/fechar")
	public String fecharSelecao(@PathVariable Integer idSelecao) {
		try {
			Selecao selecao = this.selecaoService.findById(idSelecao);
			this.selecaoService.fecharSelecao(selecao);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/adm/selecoes/" + idSelecao + "/processos";
	}

	@GetMapping("/logout")
	public String logout() {
		return "redirect:/user/logout";
	}

	@GetMapping("/avaliacao")
	public ModelAndView telaAvaliacao() {

		ModelAndView mv = new ModelAndView("/adm/avaliacao");
		return mv;
	}
}
