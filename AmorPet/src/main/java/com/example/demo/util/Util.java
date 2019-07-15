package com.example.demo.util;

import java.io.File;

public class Util {

	public static String limparMascaraTelefone(String telefone) {
		return telefone.replaceAll("[\\(\\)\\.\\-]", "");
	}

	public static String caminhoParaImagem(String nomeFoto) {
		String caminhoCompleto = new File("").getAbsolutePath()
				+ "/src/main/resources/static/img/usuarios/";
		new File(caminhoCompleto).mkdirs();
		caminhoCompleto += nomeFoto;
		return caminhoCompleto;
	}
}
