package br.uece.computacao.integralizaac.business;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.uece.computacao.integralizaac.dao.PeriodoDao;
import br.uece.computacao.integralizaac.entity.Periodo;
import br.uece.computacao.integralizaac.exceptions.BusinessException;
import br.uece.computacao.integralizaac.utils.MsgUtil;

/**
 * @author Jocélio Otávio
 *
 * Classe responsável pelas regras de negócio da entidade
 * @see Periodo.
 */
public class PeriodoBO extends Business<Periodo> {
	/**
	 * Objeto da classe de persistencia da entidade @see Periodo.
	 */
	private PeriodoDao periodoDao;
	
	/**
	 * Objeto da classe mensagens do sistema. 
	 */
	private MsgUtil msgUtil;
	
	public PeriodoBO(PeriodoDao dao) {
		super(dao);
		periodoDao = dao;
		msgUtil = new MsgUtil();
	}
	
	/**
	 * Método que retorna uma lista com todos os
	 * períodos cadastrados na base de dados.
	 * 
	 * @return Lista de períodos
	 */
	public List<Periodo> listarTodosPeriodos() {
		List<Periodo> todosPeriodos = periodoDao.listarTodos();
		
		Collections.sort(todosPeriodos, new Comparator<Periodo>() {
			@Override
			public int compare(Periodo o1, Periodo o2) {
				return o2.getDataFim().compareTo(o1.getDataFim());
			}
			
		});
		
		return todosPeriodos; 
	}
	
	/* (non-Javadoc)
	 * @see br.uece.computacao.integralizaac.business.Business#incluir(br.uece.computacao.integralizaac.entity.BaseEntity)
	 * 
	 * Método de inclusão do periodo que valida o período antes
	 * da inclusão.
	 * 
	 */
	@Override
	public void incluir(Periodo periodo) {
		validarPeriodo(periodo);
		
		periodoDao.incluir(periodo);
	}

	/* (non-Javadoc)
	 * @see br.uece.computacao.integralizaac.business.Business#atualizar(br.uece.computacao.integralizaac.entity.BaseEntity)
	 * 
	 * Método de alteração que valida o período antes
	 * da alteração. 
	 */
	@Override
	public void atualizar(Periodo periodo) {
		validarPeriodo(periodo);
		
		periodoDao.atualizar(periodo);
	}
	
	/**
	 * Método de validação da entidade Periodo que verifica se
	 * existe período no banco com o mesmo nome, ou se o período
	 * seja concomitante com outro já cadastrado.
	 * 
	 * @param periodo Período a ser validado.
	 */
	protected void validarPeriodo(Periodo periodo) {
		List<Periodo> periodos = periodoDao.buscarPeriodoComNome(periodo.getNome());
		
		if (periodos != null) {
			
			if ((!periodo.isPesistido() && periodos.size() == 1) 
					|| (periodo.isPesistido() && !periodos.isEmpty() && !periodo.equals(periodos.get(0)))) {
				throw new BusinessException(msgUtil.getMessage("periodo.periodoJaCadastrado"));
			}
		}
		
		if (periodo.getDataInicio() != null && periodo.getDataFim() != null) {
			if (periodo.getDataInicio().compareTo(periodo.getDataFim()) > 0) {
				throw new BusinessException(msgUtil.getMessage("periodo.dataInicioMaiorDataFim"));
			}
			
			if (!periodoDao.buscarPeriodosConcomitantes(periodo).isEmpty()) {
				throw new BusinessException(msgUtil.getMessage("periodo.existeConcomitante"));
			}
		}
	}
}
