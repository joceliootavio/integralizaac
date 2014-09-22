package br.uece.computacao.integralizaac.dto;

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
