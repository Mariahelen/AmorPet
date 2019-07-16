package com.example.demo.util;

import java.io.File;

public class Util {

	public static String limparMascaraTelefone(String telefone) {
		return telefone.replaceAll("[\\(\\)\\.\\-]", "");
	}

	public static String caminhoParaImagemUsuario(String nomeFoto) {
		String caminhoCompleto = new File("").getAbsolutePath()
				+ "/src/main/resources/static/img/usuarios/";
		new File(caminhoCompleto).mkdirs();
		caminhoCompleto += nomeFoto;
		return caminhoCompleto;
	}
	public static String caminhoParaImagemAnimal(String nomeFoto) {
		String caminhoCompleto = new File("").getAbsolutePath()
				+ "/src/main/resources/static/img/animais/";
		new File(caminhoCompleto).mkdirs();
		caminhoCompleto += nomeFoto;
		return caminhoCompleto;
	}
}
