package br.uece.computacao.integralizaac.dto;

/**
 * @author Jocélio Otávio
 * 
 * Classe que contém as informações do total de horas do aluno
 * em uma determinada Atividade complementar.
 * 
 */
public class HorasAtividadeDTO {
	private int somaHorasPeriodo;
	private int somaHorasCurso;
	
	public HorasAtividadeDTO(int somaHorasPeriodo, int somaHorasCurso) {
		super();
		this.somaHorasPeriodo = somaHorasPeriodo;
		this.somaHorasCurso = somaHorasCurso;
	}
	
	public int getSomaHorasPeriodo() {
		return somaHorasPeriodo;
	}
	public int getSomaHorasCurso() {
		return somaHorasCurso;
	}
	
}
