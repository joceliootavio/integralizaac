package br.uece.computacao.integralizaac.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.uece.computacao.integralizaac.entity.AtividadeComplementar;
import br.uece.computacao.integralizaac.entity.Curso;
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
		return buscarComDescricao(null, natureza, descricao, exatamente);
	}
	
	/**
	 * Recupera as atividades complementares de uma determinada natureza
	 * filtrando pela descrição, sendo uma pesquisa exata ou não.
	 * 
	 * @param curso Curso no qual o aluno está matriculado 
	 * @param natureza Natureza das atividades
	 * @param descricao Descrição total ou parcial da atividade
	 * @param exatamente Se true buscará a atividade que tenha o nome 
	 * exato, se false buscará atividades com descrição que contenha o 
	 * parâmetro informado
	 *  
	 * @return Lista de Atividades Complementares
	 */
	public List<AtividadeComplementar> buscarComDescricao(Curso curso, NaturezaEnum natureza, String descricao, boolean exatamente) {
		
		StringBuilder hql = new StringBuilder()
			.append("select a from AtividadeCurso as ac  ")
			.append("join ac.atividade as a ")
			.append("where a.natureza = :natureza ")
			.append("and UPPER(a.descricao) like UPPER(:descAtividade) ");
		
		if (curso != null && curso.getId() != 0) {
			hql.append("and ac.curso.id = :cursoId ");
		}
		
		TypedQuery<AtividadeComplementar> query = getEntityManager()
				.createQuery(hql.toString(), AtividadeComplementar.class);
		
		query.setParameter("natureza", natureza);
		if (exatamente) {
			query.setParameter("descAtividade", descricao);
		} else {
			query.setParameter("descAtividade", "%" + descricao + "%");
		}
		
		if (curso != null && curso.getId() != 0) {
			query.setParameter("cursoId", curso.getId());
		}
		
		return query.getResultList();
	}
	
	/**
	 * Método que verifica se tem aluno utilizando a atividade passada como
	 * parâmetro.
	 *  
	 * @param atividadeComplementarId ID da atividade.
	 * @return Quantidade de alunos utilizando a atividade
	 */
	public long verificaAtividadeUtilizada(Long atividadeComplementarId, Curso curso) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select COUNT(a) from AtividadeAluno as a  ");
		hql.append("where a.atividade.id = :atividadeComplementarId ");
		hql.append("and a.aluno.curso.id = :cursoId ");		
		
		Query query = getEntityManager()
				.createQuery(hql.toString());
		
		query.setParameter("atividadeComplementarId", atividadeComplementarId);		
		query.setParameter("cursoId", curso.getId());
		
		return (Long) query.getSingleResult();
	}

}
