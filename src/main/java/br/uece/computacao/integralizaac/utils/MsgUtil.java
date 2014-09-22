package br.uece.computacao.integralizaac.utils;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class MsgUtil {
	private static ResourceBundle messages;

	public MsgUtil() {

		if (messages == null) {
			messages = ResourceBundle.getBundle("br.uece.computacao.integralizaac.bundles.CustomMessages");
		}
	}

	public String getMessage(String messageKey) {
		return messages.getString(messageKey);
	}

	public String getMessage(String messageKey, Object... params) {
		return MessageFormat.format(messages.getString(messageKey), params);
	}
}
