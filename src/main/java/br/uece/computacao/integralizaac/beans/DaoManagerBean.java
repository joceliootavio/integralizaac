package br.uece.computacao.integralizaac.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ManagedBean
@RequestScoped
public class DaoManagerBean extends AbstractBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4783836689892763088L;

	public static final EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("integralizaacPU");
	
	private EntityManager em;

	@PostConstruct
	public void init() {
		em = emFactory.createEntityManager();
		em.getTransaction().begin();
	}
	
	@PreDestroy
	public void commitTransaction() throws Throwable {
		
		try {  
            em.getTransaction().commit();  
  
        } catch (Throwable ex) {  
            ex.printStackTrace();  
            
            try {  
                if (em.getTransaction().isActive()) {  
                    em.getTransaction().rollback();  
                }  
            } catch (Throwable rbEx) {  
            	rbEx.printStackTrace();
                System.out.println("Could not rollback transaction after exception!");  
            }  
  
            throw ex;  
        }  
	}

	public EntityManager getEm() {
		return em;
	}
	
}
