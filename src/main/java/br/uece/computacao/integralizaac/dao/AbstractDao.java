package br.uece.computacao.integralizaac.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.uece.computacao.integralizaac.exceptions.DAOException;

/**
 * @author Jocélio Otávio
 * 
 * Classe abstrata da qual deve herdar todas as classes Dao
 * da aplicação. Essa classe possui métodos utilitários 
 * prontos que serão reaproveitados nas classes filhas. 
 *
 * @param <E> Tipo da entidade que deve ser persistida.
 */
public abstract class AbstractDao <E> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Referẽncia a classe @see JPAUtil.
	 */
	private JPAUtil jpaUtil;
	
	/**
	 * Referência a classe do tipo genérico declarado junto
	 * a classe. Serve para facilitar as querys de busca
	 * genéricas implementadas nessa classe
	 */
	protected Class<E> entityClass;
	
	@SuppressWarnings("unchecked")
	public AbstractDao() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
	    this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[0];
	    
	    jpaUtil = new JPAUtil();
	}

	/**
	 * Inclui o objeto atual na base de dados.
	 * 
	 * @param objeto
	 *            a ser salvo
	 */
	public void incluir(E objeto) {
		try {
			getEntityManager().persist(objeto);
		} catch (RuntimeException e) {
			handleException(e);
		}
	}

	/**
	 * Exclui o objeto da base de dados.
	 * 
	 * @param objeto a ser removido
	 */
	public final void excluir(E objeto) {
		try {
			getEntityManager().remove(getEntityManager().merge(objeto));
		} catch (RollbackException e) {
			if (e.getCause() instanceof PersistenceException) {
				throw new DAOException("Não foi possível excluir. Verifique referências cruzadas.");
			}
			handleException(e);
		} catch (RuntimeException e) {
			handleException(e);
		}
	}

	/**
	 * Executa o merge do objeto que se encontra em memória.
	 * 
	 * @param objeto a ser realizado o merge
	 * @return objeto que foi executado o merge
	 */
	public E atualizar(E objeto) {
		try {
			objeto = getEntityManager().merge(objeto);
		} catch (RuntimeException e) {
			handleException(e);
		}

		return null;
	}
	
	/**
	 * Método que lista todos os objetos do tipo genérico.
	 * @return
	 */
	public List<E> listarTodos() {
        CriteriaBuilder cb = jpaUtil.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(entityClass);
        Root<E> rootEntry = cq.from(entityClass);
        CriteriaQuery<E> all = cq.select(rootEntry);
        TypedQuery<E> allQuery = jpaUtil.getEntityManager().createQuery(all);
        return allQuery.getResultList();
	}

	/**
	 * Atualiza o objeto que se encontra em memória.
	 * 
	 * @param object objeto a ser atualizado
	 */
	public final void refresh(E object) {
		getEntityManager().refresh(object);
	}

	/**
	 * Utilizado para se utilizar o entity manager nos DAOS que herdam do DAO
	 * genérico.
	 * 
	 * @return Entity manager.
	 */
	protected EntityManager getEntityManager() {
		return jpaUtil.getEntityManager();
	}

	/**
	 * Executa o flush no entity manager.
	 * 
	 */
	public final void flush() {
		getEntityManager().setFlushMode(FlushModeType.COMMIT);
		getEntityManager().flush();
	}

	/**
	 * Executa o clear no entity manager.
	 * 
	 */
	public final void clear() {
		flush();
		getEntityManager().clear();
	}

	/**
	 * Método que recupera o objeto através do id passado
	 * como parâmetro
	 * 
	 * @param id Id do objeto
	 * @return O objeto
	 */
	public E buscaPorId(long id) {
		return getEntityManager().find(entityClass, id);
	}

	/**
	 * Método que lança exceção do tipo @see DAOException utilizando
	 * a exceção passada como parâmetro como raiz.
	 * 
	 * @param em Exceção ocorrida.
	 * @throws DAOException Instância da classe de exceção do DAO.
	 */
	protected void handleException(RuntimeException e) throws DAOException {
		throw new DAOException(e);
	}

	/**
	 * Atualiza a sessão hibernate do objeto passado como parâmetro. 
	 * 
	 * @param entidade
	 */
	public void atualizarSessao(Object entidade) {
		jpaUtil.getEntityManager().merge(entidade);
	}

}