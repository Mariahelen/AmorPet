package com.example.demo.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Usuario;

public class Util {

	public static String limparMascaraTelefone(String telefone) {
		return telefone.replaceAll("[\\(\\)\\.\\-]", "");
	}
	
	public static String criptografarSenha(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
		byte messageDigestSenha[] = algorithm.digest(senha.getBytes("UTF-8"));

		StringBuilder hexStringSenha = new StringBuilder();
		for (byte b : messageDigestSenha) {
			hexStringSenha.append(String.format("%02X", 0xFF & b));
		}
		// senha criptografada
		return hexStringSenha.toString();
	}

	public static String caminhoParaImagemUsuario(String nomeFoto) {
		String caminhoCompleto = new File("").getAbsolutePath() + "/src/main/resources/static/img/usuarios/";
		new File(caminhoCompleto).mkdirs();
		caminhoCompleto += nomeFoto;
		return caminhoCompleto;
	}

	public static String pegarCaminhoCompletoParaImagemAnimal(String nomeFoto, String tipoAnimal) throws Exception {
		String diretorios = "/src/main/resources/static";
		String caminhoCompleto = new File("").getAbsolutePath() + diretorios;
		caminhoCompleto += pegarCaminhoImagemAnimal(tipoAnimal);
		new File(caminhoCompleto).mkdirs();
		caminhoCompleto += nomeFoto;
		return caminhoCompleto;
	}
	
	/**
	 * 
	 * @param String tipo do animal. Ex.: Cachorro ou Gato
	 * @return Caminho para foto do animal correspondente ao tipo dele e pronta para exibição
	 * @throws Exception - Lança uma exeção se não for possível determinar o tipo do animal
	 */
	public static String pegarCaminhoImagemAnimal(String tipoAnimal) throws Exception {
		String caminhoImagem;
		if(tipoAnimal.equalsIgnoreCase("Cachorro")) {
			caminhoImagem = "/img/animais/dogs/";
		} else if(tipoAnimal.equalsIgnoreCase("Gato")) {
			caminhoImagem = "/img/animais/cats/";
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
