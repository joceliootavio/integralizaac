package br.uece.computacao.integralizaac.business;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.uece.computacao.integralizaac.dao.PeriodoDao;
import br.uece.computacao.integralizaac.entity.Periodo;
import br.uece.computacao.integralizaac.exceptions.BusinessException;
import br.uece.computacao.integralizaac.utils.MsgUtil;

public class PeriodoBO extends Business<Periodo> {
	private PeriodoDao periodoDao;
	private MsgUtil msgUtil;
	
	public PeriodoBO(PeriodoDao dao) {
		super(dao);
		periodoDao = dao;
		msgUtil = new MsgUtil();
	}
	
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
	
	@Override
	public void incluir(Periodo periodo) {
		validarPeriodo(periodo);
		
		periodoDao.incluir(periodo);
	}

	@Override
	public void atualizar(Periodo periodo) {
		validarPeriodo(periodo);
		
		periodoDao.atualizar(periodo);
	}
	
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
