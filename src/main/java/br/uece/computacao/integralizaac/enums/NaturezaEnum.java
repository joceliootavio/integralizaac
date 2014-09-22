package br.uece.computacao.integralizaac.enums;

public enum NaturezaEnum {
	ENSINO("A", "Acadêmica: Ensino"), 
	PESQUISA_E_PRODUCAO("B", "Acadêmica: Pesquisa e Produção Científica"), 
	GERAL("C", "Acadêmica: Geral"), 
	EXTENSAO("D", "Acadêmica: Extensão");
	
	private String letra;	
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

	public static NaturezaEnum naturezaComLetra(String letra) {
		for (int i = 0; i < values().length; i++) {
			if (values()[i].letra.equals(letra))
				return values()[i]; 
		}
		
		return null;
	}
	
	public static NaturezaEnum naturezaComOrdinal(int ordinal) {
		for (int i = 0; i < values().length; i++) {
			if (values()[i].ordinal() == ordinal)
				return values()[i]; 
		}
		
		return null;
	}	
	
}
