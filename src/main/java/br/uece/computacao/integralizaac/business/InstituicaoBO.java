package br.uece.computacao.integralizaac.business;

import java.io.Serializable;

import javax.persistence.NoResultException;

import br.uece.computacao.integralizaac.dao.InstituicaoDao;
import br.uece.computacao.integralizaac.entity.Instituicao;
import br.uece.computacao.integralizaac.exceptions.BusinessException;
import br.uece.computacao.integralizaac.utils.MsgUtil;

/**
 * @author Jocélio Otávio
 *
 * Classe responsável pelas regras de negócio da entidade
 * @see Instituicao.
 */
public class InstituicaoBO extends Business<Instituicao> implements Serializable {
	
	/**
	 * Objeto da classe de persistencia da entidade @see Instituicao
	 */	
	private InstituicaoDao instituicaoDao;
	
	/**
	 * Objeto da classe mensagens do sistema. 
	 */
	private MsgUtil msgUtil;	
	
	public InstituicaoBO(InstituicaoDao dao) {
		super(dao);
		this.instituicaoDao = dao;
		this.msgUtil = new MsgUtil();
	}
	
	/* (non-Javadoc)
	 * @see br.uece.computacao.integralizaac.business.Business#incluir(br.uece.computacao.integralizaac.entity.BaseEntity)
	 * 
	 * Método de inclusão que verifica se já existe uma instituição com
	 * o mesmo nome da instiuição a ser incluída.
	 * 
	 */
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

	
	
	
	
	

	
	