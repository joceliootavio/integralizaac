package br.uece.computacao.integralizaac.utils;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @author Jocélio Otávio
 * 
 * Classe responsável por acesso ao arquivo properties 
 * de mensagens específicas da aplicação.
 * 
 */
public class MsgUtil implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Representa o arquivo de mensagens.
	 */
	private static ResourceBundle messages;

	/**
	 * Construtor que carrega o arquivo properties com as mensagens
	 * do sistema.
	 */
	public MsgUtil() {

		if (messages == null) {
			messages = ResourceBundle.getBundle("br.uece.computacao.integralizaac.bundles.CustomMessages");
		}
	}

	/**
	 * Retorna uma mensagem que tenha a chave igual a 
	 * passada como parâmetro.
	 * 
	 * @param messageKey Chave da mensagem no arquivo.
	 * 
	 * @return Mensagem de texto.
	 */
	public String getMessage(String messageKey) {
		return messages.getString(messageKey);
	}

	/** Retorna uma mensagem que tenha chave igual a passada como
	 * parâmetro, e utiliza os demais parâmetros na mensagem.
	 * 
	 * @param messageKey Chave da mensagem no arquivo.
	 * 
	 * @param params Parâmetros da mensagem.
	 * 
	 * @return Mensagem
	 */
	public String getMessage(String messageKey, Object... params) {
		return MessageFormat.format(messages.getString(messageKey), params);
	}
}
