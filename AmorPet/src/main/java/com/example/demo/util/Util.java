package com.example.demo.util;

import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Usuario;

public class Util {

	public static String limparMascaraTelefone(String telefone) {
		return telefone.replaceAll("[\\(\\)\\.\\-]", "");
	}

	public static String caminhoParaImagemUsuario(String nomeFoto) {
		String caminhoCompleto = new File("").getAbsolutePath() + "/src/main/resources/static/img/usuarios/";
		new File(caminhoCompleto).mkdirs();
		caminhoCompleto += nomeFoto;
		return caminhoCompleto;
	}

	public static String pegarCaminhoCompletoParaImagemAnimal(String nomeFoto, String tipoAnimal) throws Exception {
		String caminhoCompleto = new File("").getAbsolutePath() + "/src/main/resources/static/";
		caminhoCompleto += pegarCaminhoImagemAnimal(tipoAnimal);
		new File(caminhoCompleto).mkdirs();
		caminhoCompleto += nomeFoto;
		return caminhoCompleto;
	}
	
	public static String pegarCaminhoImagemAnimal(String tipoAnimal) throws Exception {
		String caminhoImagem;
		if(tipoAnimal.equalsIgnoreCase("Cachorro")) {
			caminhoImagem = "img/animais/dogs/";
		} else if(tipoAnimal.equalsIgnoreCase("Gato")) {
			caminhoImagem = "img/animais/cats/";
		}else {
			throw new Exception("Não foi possível cadastrar");
		}
		return caminhoImagem;
	}

	public static List<String> validaUsuario(Usuario usuario) {
		List<String> errors = new ArrayList<>();
		if (usuario.getNome().trim().isEmpty()) {
			errors.add("Nome é necessário");
		}
		if (usuario.getDataNascimento() == null) {
			errors.add("Data de Nascimento é necessária");
		}
		if (usuario.getTelefone().trim().isEmpty() || usuario.getTelefone().length() != 11) {
			errors.add("Telefone inválido, o padrão é (xx)x.xxxx-xxxx");
		} else {
			try {
				Long.parseLong(usuario.getTelefone());
			} catch (NumberFormatException e) {
				errors.add("Telefone inválido, o padrão é (xx)x.xxxx-xxxx");
			}
		}
		if(errors.isEmpty()) {
			return null;
		}
		return errors;
	}
	
	public static int calcularIdade(final LocalDate aniversario) {
	    return Period.between(aniversario, LocalDate.now()).getYears();
	}
}
