package br.uece.computacao.integralizaac.utils;

import java.util.Random;

public class GeradorSenha {
	private static final char[] CARACTERES = new char[62];
	private static final Random RANDOM = new Random();

	static {
		for (int i = 48, j = 0; i < 123; i++) {
			if (Character.isLetterOrDigit(i)) {
				CARACTERES[j] = (char) i;
				j++;
			}
		}
	}

	public static String getSenhaRandomica(final int length) {
		final char[] result = new char[length];
		for (int i = 0; i < length; i++) {
			result[i] = CARACTERES[RANDOM.nextInt(CARACTERES.length)];
		}
		return new String(result);
	}

	public static String getSenhaRandomica() {
		return getSenhaRandomica(6);
	}

}
