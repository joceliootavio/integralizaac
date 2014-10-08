package br.uece.computacao.integralizaac.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import br.uece.computacao.integralizaac.entity.Periodo;

/**
 * @author Jocélio Otávio
 *
 * Classe responsável pelo acesso aos dados das entidades
 * Período.
 * 
 */
public class PeriodoDao extends
		AbstractDao<Periodo> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Método que recupera uma lista de períodos que tenha o nome
	 * igual ao nome passado como parâmetro.
	 * 
	 * @param nome Nome do período.
	 * 
	 * @return Lista de objetos Periodo.
	 */
	public List<Periodo> buscarPeriodoComNome(String nome) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select p from Periodo as p ");
		hql.append("where p.nome = :nomePeriodo ");
		
		TypedQuery<Periodo> query = getEntityManager()
				.createQuery(hql.toString(), Periodo.class);
		
		query.setParameter("nomePeriodo", nome);
		
		return query.getResultList();
	}

	/**
	 * Busca períodos que tenha datas concomitantes as datas
	 * informadas para o novo período passado como parâmetro.
	 * 
	 * @param periodo Período
	 * 
	 * @return Lista de períodos
	 */
	public List<Periodo> buscarPeriodosConcomitantes(Periodo periodo) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select p from Periodo as p ");
		hql.append("where 1=1 ");
		
		if (periodo.getId() != null) {
			hql.append("and p.id != :periodoId ");
		}
		
		hql.append("and ((p.dataInicio > :dataInicio and p.dataInicio < :dataFim) ");
		hql.append("or (p.dataInicio < :dataInicio and p.dataFim > :dataInicio)) ");
		
		TypedQuery<Periodo> query = getEntityManager()
				.createQuery(hql.toString(), Periodo.class);
		
		if (periodo.getId() != null) {
			query.setParameter("periodoId", periodo.getId());
		}
		
		query.setParameter("dataInicio", periodo.getDataInicio());
		query.setParameter("dataFim", periodo.getDataFim());		
		
		return query.getResultList();
	}
}
