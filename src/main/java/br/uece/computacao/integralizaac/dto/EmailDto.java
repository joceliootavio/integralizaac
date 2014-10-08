package br.uece.computacao.integralizaac.dto;

/**
 * @author Jocélio Otávio
 * Classe que contém as informações necessárias para envio
 * de email pela aplicação.
 * 
 */
public class EmailDto {
	/**
	 * Assunto do email.
	 */
	private String assunto;
	
	/**
	 * Destinatários do email separados por ','. 
	 */
	private String destinatarios;
	
	/**
	 * Corpo do email.
	 */
	private String corpo;
	
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getDestinatarios() {
		return destinatarios;
	}
	public void setDestinatarios(String destinatarios) {
		this.destinatarios = destinatarios;
	}
	public String getCorpo() {
		return corpo;
	}
	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}
}
