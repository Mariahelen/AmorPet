package com.example.demo.util;

import java.io.File;

public class Utilidade {

	public static String limparMascaraTelefone(String telefone) {
		return telefone.replaceAll("[\\(\\)\\.\\-]", "");
	}

	public static String caminhoParaImagem(String nomeFoto) {
		File caminhoRaiz = new File("");
		String caminhoCompleto = caminhoRaiz.getAbsolutePath()
				+ "/src/main/resources/static/img/usuarios/" + nomeFoto;
		return caminhoCompleto;
	}
}
