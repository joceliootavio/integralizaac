package br.uece.computacao.integralizaac.dao;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;

public class JPAUtil {

	private static final EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("integralizaacPU");
	
	public EntityManager createEntityManager() {
		return emFactory.createEntityManager();
	}
	
	public EntityManager getEntityManager() {
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		return (EntityManager) req.getAttribute("entityManager");		
	}
	
	public void closeEmFactory() {
		if (emFactory != null)
			emFactory.close();
	}
	
}

