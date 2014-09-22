package br.uece.computacao.integralizaac.business;

import javax.persistence.NoResultException;

import br.uece.computacao.integralizaac.dao.InstituicaoDao;
import br.uece.computacao.integralizaac.entity.Instituicao;
import br.uece.computacao.integralizaac.exceptions.BusinessException;
import br.uece.computacao.integralizaac.utils.MsgUtil;

public class InstituicaoBO extends Business<Instituicao> {
	
	private InstituicaoDao instituicaoDao;
	private MsgUtil msgUtil;	
	
	public InstituicaoBO(InstituicaoDao dao) {
		super(dao);
		this.instituicaoDao = dao;
		this.msgUtil = new MsgUtil();
	}
	
	@Override
	public void incluir(Instituicao instituicao) {
		try {
			if (instituicaoDao.buscarPorNome(instituicao.getNome()) != null) {
				throw new BusinessException(msgUtil.getMessage("instituicao.instituicaoJaCadastrada"));
			}
		} catch (NoResultException nre) {}
		
		dao.incluir(instituicao);
	}
}

	
	
	
	
	

	
	