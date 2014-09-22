package br.uece.computacao.integralizaac.business;

import java.util.List;

import br.uece.computacao.integralizaac.dao.AbstractDao;
import br.uece.computacao.integralizaac.entity.BaseEntity;

public class Business<T extends BaseEntity> {
	
	protected AbstractDao<T> dao;

	public Business(AbstractDao<T> dao) {
		super();
		this.dao = dao;
	}

	public void incluir(T entidade) {
		dao.incluir(entidade);
	}
	
	public void atualizar(T entidade) {
		dao.atualizar(entidade);
	}
	
	public void excluir(T entidade) {
		dao.excluir(entidade);
	}
	
	public T buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
	public void atualizarSessao(Object entidade) {
		dao.atualizarSessao(entidade);
	}
	
	public List<T> listarTodos() {
		return dao.listarTodos();
	}
}