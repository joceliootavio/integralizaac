package br.uece.computacao.integralizaac.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

/**
 * @author Jocélio Otávio
 *
 * Classe que contém métodos utilitários para manipulação de
 * arquivos.
 *
 */
public class FileUtils {

	/**
	 * Deleta o arquivo passado como parâmetro sem lançar exceção.
	 */
	public static boolean deleteQuietly(File file) {
		return org.apache.commons.io.FileUtils.deleteQuietly(file);
	}

	/**
	 * Copia os dados de um InputStream para um arquivo.
	 */
	public static void copy(InputStream input, File output) throws IOException {
		FileOutputStream fos = new FileOutputStream(output);
		IOUtils.copy(input, fos);
		fos.flush();
		fos.close();
	}

	/**
	 * Copia um arquivo para o outro.
	 */
	public static void copy(File input, File output) throws IOException {
		org.apache.commons.io.FileUtils.copyFile(input, output);
	}

}
