package br.uece.computacao.integralizaac.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

public class FileUtils {

	/**
	 * Creates a temporal and unique directory
	 * 
	 * @throws IOException
	 *             If something fails.
	 */
	public static File createTempDir() throws IOException {
		File tmpFile = File.createTempFile("okm", null);

		if (!tmpFile.delete())
			throw new IOException();
		if (!tmpFile.mkdir())
			throw new IOException();
		return tmpFile;
	}

	/**
	 * Create temp file with extension from mime
	 */
	public static File createTempFileFromMime(String mimeType)
			throws IOException {
		// MimeType mt = MimeTypeDAO.findByName(mimeType);
		String ext = "pdf";// mt.getExtensions().iterator().next();
		return File.createTempFile("oix", "." + ext);
	}

	/**
	 * Wrapper for FileUtils.deleteQuietly
	 */
	public static boolean deleteQuietly(File file) {
		return org.apache.commons.io.FileUtils.deleteQuietly(file);
	}

	/**
	 * Count files and directories from a selected directory.
	 */
	public static int countFiles(File dir) {
		File[] found = dir.listFiles();
		int ret = 0;

		if (found != null) {
			for (int i = 0; i < found.length; i++) {
				if (found[i].isDirectory()) {
					ret += countFiles(found[i]);
				}

				ret++;
			}
		}

		return ret;
	}

	/**
	 * Copy InputStream to File.
	 */
	public static void copy(InputStream input, File output) throws IOException {
		FileOutputStream fos = new FileOutputStream(output);
		IOUtils.copy(input, fos);
		fos.flush();
		fos.close();
	}

	/**
	 * Copy File to OutputStream
	 */
	public static void copy(File input, OutputStream output) throws IOException {
		FileInputStream fis = new FileInputStream(input);
		IOUtils.copy(fis, output);
		fis.close();
	}

	/**
	 * Copy File to File
	 */
	public static void copy(File input, File output) throws IOException {
		org.apache.commons.io.FileUtils.copyFile(input, output);
	}

}
