package br.uece.computacao.integralizaac.business;

import java.util.List;

import br.uece.computacao.integralizaac.dao.AbstractDao;
import br.uece.computacao.integralizaac.entity.BaseEntity;

/**
 * @author Jocélio Otávio
 * 
 * Classe abstrata da qual deve herdar todas as classes Business
 * da aplicação. Essa classe possui métodos utilitários 
 * prontos que serão reaproveitados nas classes filhas. 
 *
 * @param <T> Tipo da entidade que deve ser gerenciada.
 */
public class Business<T extends BaseEntity> {
	
	/**
	 * Objeto que herde da classe AbstractDao responsável
	 * por persistir os dados da entidade T.
	 */
	protected AbstractDao<T> dao;

	public Business(AbstractDao<T> dao) {
		super();
		this.dao = dao;
	}

	/**
	 * Método de negócio responsável pela inclusão 
	 * genérica de entidades no repositório.
	 * 
	 * @param entidade Entidade a ser persistida.
	 */
	public void incluir(T entidade) {
		dao.incluir(entidade);
	}
	
	/**
	 * Método de negócio responsável pela alteração 
	 * genérica de entidades no repositório.
	 * 
	 * @param entidade Entidade a ser alterada.
	 */	
	public void atualizar(T entidade) {
		dao.atualizar(entidade);
	}
	
	/**
	 * Método de negócio responsável pela exclusão 
	 * genérica de entidades no repositório.
	 * 
	 * @param entidade Entidade a ser excluída.
	 */		
	public void excluir(T entidade) {
		dao.excluir(entidade);
	}
	
	/**
	 * Método que retorna a entidade que tenha Id
	 * igual ao Id passado como parâmetro.
	 * 
	 * @param id Id da entidade.
	 * @return Instância da Entidade
	 */
	public T buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
	/**
	 * Método que atualiza a sessão do hibernate associada
	 * a entidade passada como parâmetro.
	 * 
	 * @param entidade Entidade a ser atualizada.
	 */
	public void atualizarSessao(Object entidade) {
		dao.atualizarSessao(entidade);
	}
	
	/**
	 * Método que lista todas as entidades do tipo T
	 * cadastradas no banco.
	 * 
	 * @return Lista de objetos da classe T
	 */
	public List<T> listarTodos() {
		return dao.listarTodos();
	}
}