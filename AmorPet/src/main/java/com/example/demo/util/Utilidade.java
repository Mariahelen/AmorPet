package com.example.demo.util;

public class Utilidade {

	public static String limparMascaraTelefone(String telefone) {
		return telefone.replaceAll("[\\(\\)\\.\\-]", "");
	}
}
