package br.uece.computacao.integralizaac.enums;

/**
 * @author Jocélio Otávio
 *
 * Contém a lista de naturezas de atividades permitidas
 * no curso.
 */
public enum CentroEnum {
	
	CESA("CENTRO DE ESTUDOS SOCIAIS APLICADOS"),
	CCT("CENTRO DE CIÊNCIAS E TECNOLOGIA"),
	CCS("CENTRO DE CIÊNCIAS DA SAÚDE"),
	CH("CENTRO DE HUMANIDADES"),
	FAVET("FACULDADE DE VETERINÁRIA"),
	CED("CENTRO DE EDUCAÇÃO");	
	
	/**
	 * Descrição legível da natureza
	 */
	private String descricao;
	
	private CentroEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		return super.toString() + " - " + descricao;
	}
}
