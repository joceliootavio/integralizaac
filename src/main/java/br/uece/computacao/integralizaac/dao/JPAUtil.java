package br.uece.computacao.integralizaac.dao;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Jocélio Otávio
 * 
 * Classe utilitária responsável por criar e fornecer 
 * gerenciador de entidades. 
 *
 */
public class JPAUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Atributo único para toda aplicação, uma vez que 
	 * esse tipo de objeto é muito caro para ser
	 * instanciado mais de uma vez.
	 */
	private static final EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("integralizaacPU");
	
	/**
	 * Cria um gerenciador de entidades utilizando a fábrica de
	 * gerenciadores de entidade.
	 * 
	 * @return objeto do tipo EntityManager.
	 */
	public EntityManager createEntityManager() {
		return emFactory.createEntityManager();
	}
	
	/**
	 * Método que retorna um objeto do tipo @see EntityManager
	 * 
	 * @return
	 */
	public EntityManager getEntityManager() {
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		return (EntityManager) req.getAttribute("entityManager");		
	}
	
	/**
	 * Método que fecha a fábrica de gerenciadores de entidades.
	 * Esse método deve ser chamado ao parar a aplicação.
	 */
	public void closeEmFactory() {
		if (emFactory != null)
			emFactory.close();
	}
	
}

