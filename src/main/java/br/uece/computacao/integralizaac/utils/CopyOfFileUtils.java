package br.uece.computacao.integralizaac.utils;

import java.io.File;
import java.io.IOException;

public class CopyOfFileUtils {

	/**
	 * Wrapper for FileUtils.deleteQuietly
	 */
	public static boolean deleteQuietly(File file) {
		return org.apache.commons.io.FileUtils.deleteQuietly(file);
	}

	/**
	 * Copy File to File
	 */
	public static void copy(File input, File output) throws IOException {
		org.apache.commons.io.FileUtils.copyFile(input, output);
	}

}
