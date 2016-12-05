package br.uece.computacao.integralizaac.business;

import java.util.List;

import br.uece.computacao.integralizaac.dao.AtividadeCursoDao;
import br.uece.computacao.integralizaac.entity.AtividadeComplementar;
import br.uece.computacao.integralizaac.entity.AtividadeCurso;
import br.uece.computacao.integralizaac.entity.Curso;

/**
 * @author Jocélio Otávio
 *
 * Classe responsável pelas regras de negócio da entidade
 * @see AtividadeComplementar.
 */
public class AtividadeCursoBO extends Business<AtividadeCurso> {
	
	/**
	 * Objeto da classe de persistencia da entidade @see AtividadeCurso
	 */
	private AtividadeCursoDao atividadeCursoDao;
	
	public AtividadeCursoBO(AtividadeCursoDao dao) {
		super(dao);
		this.atividadeCursoDao = dao;
	}
	
	public List<AtividadeComplementar> listaAtividadesCurso(Long cursoId) {
		return atividadeCursoDao.listaAtividadesCurso(cursoId);
	}
	
	public void atualizar(Curso curso, List<AtividadeComplementar> atividadesSelecionadas) {
		AtividadeCurso atividadeCurso = null;
		
		atividadeCursoDao.removerAtividadesCurso(curso.getId());
		
		for (AtividadeComplementar ac : atividadesSelecionadas) {
			atividadeCurso = new AtividadeCurso();
			atividadeCurso.setCurso(curso);
			atividadeCurso.setAtividade(ac);
			atividadeCursoDao.incluir(atividadeCurso);
		}
	}
	
}
