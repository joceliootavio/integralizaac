package br.uece.computacao.integralizaac.utils;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Jocélio Otávio
 *
 * Classe responsável pelo acesso ao arquivo de recursos 
 * configurados para a aplicação.
 * 
 */
public class ResourcesProvider implements Serializable {

	private static final String NOT_FOUND = "not found";
	private static final String THREE_QUESTION = "???";
	private static final long serialVersionUID = 4864944723005088944L;

	/**
	 * Método que inicializa o arquivo properties de recursos.
	 * 
	 * @return
	 */
	public ResourceBundle getBundle() {
		return ResourceBundle.getBundle("br.uece.computacao.integralizaac.bundles.Resources");
	}

	/**
	 * Retorna o valor de uma determinada chave no arquivo.
	 * 
	 * @param key Chave da mensagem
	 * 
	 * @return Mensagem
	 */
	public String getValue(String key) {

		String result = null;
		try {
			result = getBundle().getString(key);
		} catch (MissingResourceException e) {
			result = "???" + key + "??? not found";
		}
		return result;
	}

	/**
	 * Método retorna a própria chave quando a mesma não
	 * for encontrada no arquivo properties.
	 * 
	 * @param key Chave da mensagem
	 * 
	 * @return Mensagem
	 */
	public String getValueN(String key) {
		String result = null;
		try {
			result = getBundle().getString(key);
		} catch (MissingResourceException e) {
			result = key;
		}
		return result;
	}

    /**
     * Retorna a mensagem que tem a chave igual a chave passada
     * como parâmetro, e os demais parâmetros são da mensagem.
     * 
     * @param key Chave da mensagem.
     * @param params Parâmetros da mensagem.
     * 
     * @return Mensagem de texto.
     */
    public String getValueWithParam(String key, Object... params ) {
    	
    	String result = null;
        try {
        	result = MessageFormat.format(getValue(key), params);
        } catch (MissingResourceException e) {
        	result = THREE_QUESTION + key + THREE_QUESTION + " " + NOT_FOUND;
        }
        
        return result;
    }
	
}
