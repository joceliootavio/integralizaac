package br.uece.computacao.integralizaac.utils;

import java.util.Random;

/**
 * @author Jocélio Otávio
 *
 * Classe responsável por gerar senha automática, que deve
 * ser enviada para os usuários.
 *
 */
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

	/**
	 * Gera senha randômica de um determinado tamanho.
	 * 
	 * @param length Tamanho da senha que será gerada.
	 * 
	 * @return Senha randômica gerada
	 */
	public static String getSenhaRandomica(final int length) {
		final char[] result = new char[length];
		for (int i = 0; i < length; i++) {
			result[i] = CARACTERES[RANDOM.nextInt(CARACTERES.length)];
		}
		return new String(result);
	}

	/**
	 * Gera senha randômica no tamanho padrão da aplicação.
	 * 
	 * @return
	 */
	public static String getSenhaRandomica() {
		return getSenhaRandomica(6);
	}

}
