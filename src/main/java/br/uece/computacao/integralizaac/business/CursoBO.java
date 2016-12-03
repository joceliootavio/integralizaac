package br.uece.computacao.integralizaac.business;

import java.util.List;

import br.uece.computacao.integralizaac.dao.CursoDao;
import br.uece.computacao.integralizaac.entity.Curso;
import br.uece.computacao.integralizaac.exceptions.BusinessException;
import br.uece.computacao.integralizaac.utils.MsgUtil;

/**
 * @author Jocélio Otávio
 *
 * Classe responsável pelas regras de negócio da entidade
 * @see Curso.
 */
public class CursoBO extends Business<Curso> {
	/**
	 * Objeto da classe de persistencia da entidade @see Curso.
	 */
	private CursoDao cursoDao;
	
	/**
	 * Objeto da classe mensagens do sistema. 
	 */
	private MsgUtil msgUtil;
	
	public CursoBO(CursoDao dao) {
		super(dao);
		cursoDao = dao;
		msgUtil = new MsgUtil();
	}
	
	/* (non-Javadoc)
	 * @see br.uece.computacao.integralizaac.business.Business#incluir(br.uece.computacao.integralizaac.entity.BaseEntity)
	 * 
	 * Método de inclusão do curso que valida o período antes
	 * da inclusão.
	 * 
	 */
	@Override
	public void incluir(Curso curso) {
		validarCurso(curso);
		
		cursoDao.incluir(curso);
	}

	/* (non-Javadoc)
	 * @see br.uece.computacao.integralizaac.business.Business#atualizar(br.uece.computacao.integralizaac.entity.BaseEntity)
	 * 
	 * Método de alteração que valida o período antes
	 * da alteração. 
	 */
	@Override
	public void atualizar(Curso curso) {
		validarCurso(curso);
		
		cursoDao.atualizar(curso);
	}
	
	/**
	 * Método de validação da entidade Curso que verifica se
	 * existe período no banco com o mesmo nome, ou se o período
	 * seja concomitante com outro já cadastrado.
	 * 
	 * @param curso Período a ser validado.
	 */
	protected void validarCurso(Curso curso) {
		List<Curso> cursos = cursoDao.buscarCursoComNome(curso.getNome());
		
		if (cursos != null) {
			
			if ((!curso.isPesistido() && cursos.size() == 1) 
					|| (curso.isPesistido() && !cursos.isEmpty() && !curso.equals(cursos.get(0)))) {
				throw new BusinessException(msgUtil.getMessage("curso.cursoJaCadastrado"));
			}
		}
		
		if (curso.getDataImplantacao() != null && curso.getDataEncerramento() != null) {
			if (curso.getDataImplantacao().compareTo(curso.getDataEncerramento()) > 0) {
				throw new BusinessException(msgUtil.getMessage("curso.dataInicioMaiorDataFim"));
			}
		}
	}
	
	/**
	 * Método que retorna uma lista com os cursos vigentes cadastrados na base de dados.
	 * 
	 * @return Lista de cursos vigentes
	 */
	public List<Curso> listarCursosVigentes(Long idCursoExistente) {
		return cursoDao.listarCursosVigentes(idCursoExistente); 
	}
}
