package br.uece.computacao.integralizaac.utils;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ResourcesProvider implements Serializable {

	private static final String NOT_FOUND = "not found";
	private static final String THREE_QUESTION = "???";
	private static final long serialVersionUID = 4864944723005088944L;

	public ResourceBundle getBundle() {
		return ResourceBundle.getBundle("br.uece.computacao.integralizaac.bundles.Resources");
	}

	public String getValue(String key) {

		String result = null;
		try {
			result = getBundle().getString(key);
		} catch (MissingResourceException e) {
			result = "???" + key + "??? not found";
		}
		return result;
	}

	public String getValueN(String key) {
		String result = null;
		try {
			result = getBundle().getString(key);
		} catch (MissingResourceException e) {
			result = key;
		}
		return result;
	}

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
