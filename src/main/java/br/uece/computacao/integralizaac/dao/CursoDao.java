package br.uece.computacao.integralizaac.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import br.uece.computacao.integralizaac.entity.Curso;

/**
 * @author Jocélio Otávio
 *
 * Classe responsável pelo acesso aos dados das entidades
 * Curso.
 * 
 */
public class CursoDao extends
		AbstractDao<Curso> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Método que recupera uma lista de cursos que tenha o nome
	 * igual ao nome passado como parâmetro.
	 * 
	 * @param nome Nome do curso.
	 * 
	 * @return Lista de objetos Curso.
	 */
	public List<Curso> buscarCursoComNome(String nome) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select c from Curso as c ");
		hql.append("where c.nome = :nomeCurso ");
		
		TypedQuery<Curso> query = getEntityManager()
				.createQuery(hql.toString(), Curso.class);
		
		query.setParameter("nomeCurso", nome);
		
		return query.getResultList();
	}

	
	/**
	 * Método que recupera a lista de cursos vigentes, incluindo o curso referenciado
	 * por um objeto já salvo na base.
	 * 
	 * @param idCursoExistente Id do curso já selecionado e persistido.
	 * 
	 * @return Lista de objetos curso
	 */
	public List<Curso> listarCursosVigentes(Long idCursoExistente) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select c from Curso as c ");
		hql.append("where (c.dataEncerramento is null or c.dataEncerramento >= :dataAtual) " );
		
		if (idCursoExistente != 0) {
			hql.append("or c.id = :idCursoExistente");
		}
		
		TypedQuery<Curso> query = getEntityManager()
				.createQuery(hql.toString(), Curso.class);
		
		query.setParameter("dataAtual", new Date());
		
		if (idCursoExistente != 0) {
			query.setParameter("idCursoExistente", idCursoExistente);
		}
		
		return query.getResultList();
	}

}

	