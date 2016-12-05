package br.uece.computacao.integralizaac.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.uece.computacao.integralizaac.entity.AtividadeComplementar;
import br.uece.computacao.integralizaac.entity.AtividadeCurso;

/**
 * @author Jocélio Otávio
 *
 * Classe responsável por acesso aos dados das Atividades
 * do Curso.
 */
public class AtividadeCursoDao extends
		AbstractDao<AtividadeCurso> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Método que retorna a lista de atividades de um curso
	 * do que o passado como parâmetro.
	 *  
	 * @param cursoId ID do curso.
	 * @return
	 */
	public List<AtividadeComplementar> listaAtividadesCurso(Long cursoId) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select a from AtividadeCurso as ac  ");
		hql.append("join ac.atividade as a ");
		hql.append("where ac.curso.id = :cursoId ");
		
		TypedQuery<AtividadeComplementar> query = getEntityManager()
				.createQuery(hql.toString(), AtividadeComplementar.class);
		
		query.setParameter("cursoId", cursoId);
		
		return query.getResultList();
	}
	
	/**
	 * Método que retorna remove todas as atividades do curso.
	 *  
	 * @param cursoId ID do curso.
	 * @return
	 */
	public int removerAtividadesCurso(Long cursoId) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("delete from AtividadeCurso as ac  ");
		hql.append("where ac.curso.id = :cursoId ");
		
		Query query = getEntityManager()
				.createQuery(hql.toString());
		
		query.setParameter("cursoId", cursoId);
		
		return query.executeUpdate();
	}
	
}
