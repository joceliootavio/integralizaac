package br.uece.computacao.integralizaac.dao;

import javax.persistence.TypedQuery;

import br.uece.computacao.integralizaac.entity.Instituicao;

/**
 * @author Jocélio Otávio
 *
 * Classe responsável por acesso aos dados das Instituições
 * informadas pelos alunos.
 */
public class InstituicaoDao extends
		AbstractDao<Instituicao> {

	private static final long serialVersionUID = 1L;

	/**
	 * Busca a instituição pelo nome exatamente.
	 * 
	 * @param nomeInstituicao Nome da instituição
	 * @return Retorna um objeto do tipo @see Instituicao.
	 */
	public Instituicao buscarPorNome(String nomeInstituicao) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select i " );
		hql.append("from Instituicao as i ");
		hql.append("where TRIM(UPPER(i.nome)) = TRIM(UPPER(:nomeInstituicao)) ");
		
		TypedQuery<Instituicao> query = getEntityManager()
				.createQuery(hql.toString(), Instituicao.class);
		
		query.setParameter("nomeInstituicao", nomeInstituicao);
		
		return query.getSingleResult();
	}
}
