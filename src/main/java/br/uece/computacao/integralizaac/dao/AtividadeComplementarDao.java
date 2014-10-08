package br.uece.computacao.integralizaac.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import br.uece.computacao.integralizaac.entity.AtividadeComplementar;
import br.uece.computacao.integralizaac.enums.NaturezaEnum;

/**
 * @author Jocélio Otávio
 *
 * Classe responsável por acesso aos dados das Atividades
 * Complementares do curso.
 */
public class AtividadeComplementarDao extends
		AbstractDao<AtividadeComplementar> {

	private static final long serialVersionUID = 1L;

	/**
	 * Recupera as atividades complementares de uma determinada natureza
	 * filtrando pela descrição, sendo uma pesquisa exata ou não.
	 * 
	 * @param natureza Natureza das atividades
	 * @param descricao Descrição total ou parcial da atividade
	 * @param exatamente Se true buscará a atividade que tenha o nome 
	 * exato, se false buscará atividades com descrição que contenha o 
	 * parâmetro informado
	 *  
	 * @return Lista de Atividades Complementares
	 */
	public List<AtividadeComplementar> buscarComDescricao(NaturezaEnum natureza, String descricao, boolean exatamente) {
		
		StringBuilder hql = new StringBuilder()
			.append("select a from AtividadeComplementar as a ")
			.append("where a.natureza = :natureza ")
			.append("and UPPER(a.descricao) like UPPER(:descAtividade) ");		
		
		TypedQuery<AtividadeComplementar> query = getEntityManager()
				.createQuery(hql.toString(), AtividadeComplementar.class);
		
		query.setParameter("natureza", natureza);
		if (exatamente) {
			query.setParameter("descAtividade", descricao);
		} else {
			query.setParameter("descAtividade", "%" + descricao + "%");
		}
		
		return query.getResultList();
	}

}
