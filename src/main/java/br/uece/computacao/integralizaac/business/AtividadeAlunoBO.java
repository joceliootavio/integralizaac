package br.uece.computacao.integralizaac.business;

import java.util.ArrayList;
import java.util.List;

import br.uece.computacao.integralizaac.dao.AtividadeAlunoDao;
import br.uece.computacao.integralizaac.dto.HorasAtividadeDTO;
import br.uece.computacao.integralizaac.dto.ListaDashboard;
import br.uece.computacao.integralizaac.entity.Aluno;
import br.uece.computacao.integralizaac.entity.AtividadeAluno;
import br.uece.computacao.integralizaac.enums.NaturezaEnum;

/**
 * @author Jocélio Otávio
 *
 * Classe responsável pelas regras de negócio da entidade
 * @see AtividadeAluno.
 */
public class AtividadeAlunoBO extends Business<AtividadeAluno>{
	
	/**
	 * Objeto da classe de persistencia da entidade @see AtividadeAluno
	 */
	private AtividadeAlunoDao atividadeAlunoDao;
	
	public AtividadeAlunoBO(AtividadeAlunoDao dao) {
		super(dao);
		atividadeAlunoDao = dao;
	}

	/**
	 * Método que retorna <code>true</code> quando a atividade passada
	 * como parâmetro faz exceder o limite de horas para aquele tipo
	 * de atividade.
	 * 
	 * @param atividadeAluno Atividade adicionada pelo aluno.
	 * @return
	 */
	public boolean excedeuHorasAtividade(AtividadeAluno atividadeAluno) {
		atividadeAlunoDao.flush();
		
		HorasAtividadeDTO dto = atividadeAlunoDao.horasAtividade(atividadeAluno);
		
		if (atividadeAluno.getAtividade().getMaximoHorasPorPeriodo() != null 
				&& dto.getSomaHorasPeriodo() > atividadeAluno.getAtividade().getMaximoHorasPorPeriodo()) {
			return true;
		}
		
		if (atividadeAluno.getAtividade().getMaximoHorasPorCurso() != null 
				&& dto.getSomaHorasCurso() > atividadeAluno.getAtividade().getMaximoHorasPorCurso()) {
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * Método que retorna a lista de naturezas nas quais um determinado
	 * aluno tem atividades cadastradas.
	 * 
	 * @param aluno Aluno
	 * @return Lista de naturezas
	 */
	public List<NaturezaEnum> listarNaturezas(Aluno aluno) {
		if (aluno != null && aluno.getId() != null) {
			return atividadeAlunoDao.listarNaturezas(aluno);
		} else {
			return new ArrayList<NaturezaEnum>();
		} 
	}
	
	/**
	 * Método que retorna uma lista de DTOs que representam as atividades
	 * do aluno daquela natureza.
	 * 
	 * @param aluno Aluno
	 * @param natureza Natureza das atividades que serão retornadas.
	 * @return Objeto do tipo @see ListaDashboard
	 */
	public ListaDashboard listarAtividadesPorNatureza(Aluno aluno, NaturezaEnum natureza) {
		if (aluno != null && aluno.getId() != null) {
			return atividadeAlunoDao.listarAtividadesPorNatureza(aluno, natureza);
		} else {
			return new ListaDashboard();
		} 
	}
	
}
