package br.uece.computacao.integralizaac.enums;

/**
 * @author Jocélio Otávio
 *
 * Contém a lista de naturezas de atividades permitidas
 * no curso.
 */
public enum NaturezaEnum {
	
	ENSINO("A", "Acadêmica: Ensino"), 
	PESQUISA_E_PRODUCAO("B", "Acadêmica: Pesquisa e Produção Científica"), 
	GERAL("C", "Acadêmica: Geral"), 
	EXTENSAO("D", "Acadêmica: Extensão");
	
	/**
	 * Identificador utilizado pela PROGRAD no mapa de registro 
	 * de atividades
	 */
	private String letra;
	
	/**
	 * Descrição legível da natureza
	 */
	private String descricao;
	
	private NaturezaEnum(String letra, String descricao) {
		this.letra = letra;
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	public String getLetra() {
		return letra;
	}

	/**
	 * Retorna a Natureza que seja representada pela letra
	 * passada como parâmetro.
	 * 
	 * @param letra Letra que representa a natureza.
	 * 
	 * @return Natureza
	 */
	public static NaturezaEnum naturezaComLetra(String letra) {
		for (int i = 0; i < values().length; i++) {
			if (values()[i].letra.equals(letra))
				return values()[i]; 
		}
		
		return null;
	}
	
	/**
	 * Retorna a Natureza pela sua ordem na lista de Naturezas
	 * do enum.
	 *  
	 * @param ordinal Ordem da natureza.
	 * 
	 * @return Natureza
	 */
	public static NaturezaEnum naturezaComOrdinal(int ordinal) {
		for (int i = 0; i < values().length; i++) {
			if (values()[i].ordinal() == ordinal)
				return values()[i]; 
		}
		
		return null;
	}	
	
}
