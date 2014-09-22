package br.uece.computacao.integralizaac.business;

import java.util.List;

import br.uece.computacao.integralizaac.dao.AtividadeComplementarDao;
import br.uece.computacao.integralizaac.entity.AtividadeComplementar;
import br.uece.computacao.integralizaac.enums.NaturezaEnum;
import br.uece.computacao.integralizaac.exceptions.BusinessException;
import br.uece.computacao.integralizaac.utils.MsgUtil;

public class AtividadeComplementarBO extends Business<AtividadeComplementar> {
	private AtividadeComplementarDao ativComplementarDao;
	private MsgUtil msgUtil;
	
	public AtividadeComplementarBO(AtividadeComplementarDao dao) {
		super(dao);
		this.ativComplementarDao = dao;
		this.msgUtil = new MsgUtil();		
	}
	
	@Override
	public void incluir(AtividadeComplementar atividadeComplementar) {
		validarAtividadeComplementar(atividadeComplementar);
		
		dao.incluir(atividadeComplementar);
	}
	
	@Override
	public void atualizar(AtividadeComplementar atividadeComplementar) {
		validarAtividadeComplementar(atividadeComplementar);
		
		dao.atualizar(atividadeComplementar);
	}	
	
	private void validarAtividadeComplementar(AtividadeComplementar atividadeComplementar) {
		List<AtividadeComplementar> atividades = ativComplementarDao.buscarComDescricao(atividadeComplementar.getNatureza(), 
																		atividadeComplementar.getDescricao(), 
																		true);
		
		if (atividades != null) {
			
			if ((!atividadeComplementar.isPesistido() && atividades.size() == 1) 
					|| (atividadeComplementar.isPesistido() && !atividades.isEmpty() && !atividadeComplementar.equals(atividades.get(0)))) {
				throw new BusinessException(msgUtil.getMessage("atividade_complementar.atividadeJaCadastrada"));
			}
		}
	}
	
	public AtividadeComplementar buscaPorCodAtividade(long codAtividade) {
		return ativComplementarDao.buscaPorId(codAtividade);
	}
	
	public List<AtividadeComplementar> listarTodasAtividades() {
		return ativComplementarDao.listarTodos();
	}

	public List<AtividadeComplementar> buscarComDescricao(NaturezaEnum natureza, String query) {
		return ativComplementarDao.buscarComDescricao(natureza, query, false);
	}
}
