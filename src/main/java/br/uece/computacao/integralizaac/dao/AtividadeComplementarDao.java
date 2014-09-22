package br.uece.computacao.integralizaac.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import br.uece.computacao.integralizaac.entity.AtividadeComplementar;
import br.uece.computacao.integralizaac.enums.NaturezaEnum;

public class AtividadeComplementarDao extends
		AbstractDao<AtividadeComplementar> {

	private static final long serialVersionUID = 1L;

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
