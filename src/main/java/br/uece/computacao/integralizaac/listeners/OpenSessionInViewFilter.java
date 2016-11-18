package br.uece.computacao.integralizaac.listeners;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import br.uece.computacao.integralizaac.dao.JPAUtil;
import br.uece.computacao.integralizaac.exceptions.BusinessException;

/**
 * @author Jocélio Otávio
 *
 * Filtro responsável por abrir e fechar conexão com o banco
 * no início e no fim da requisição, respectivamente.
 * 
 */
public class OpenSessionInViewFilter implements Filter {

	private JPAUtil jpaUtil;
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;

		EntityManager em = null;
		try {  
			em = jpaUtil.createEntityManager();
			
			request.setAttribute("entityManager", em);
			
			em.getTransaction().begin();		
			
			chain.doFilter(req, res);
			
            em.getTransaction().commit();
        } catch (BusinessException ex) {  
        	System.out.println("BusinessException: " + ex.getMessage());
            
            threatRollback(em);  
  
            throw new ServletException(ex);      
        } catch (Throwable ex) {  
            ex.printStackTrace();
            
            threatRollback(em);  
  
            throw new ServletException(ex);  
        } finally {
            if (em != null) {
            	em.close();
            }
        }  

	}

	private void threatRollback(EntityManager em) {
		try {  
			if (em.getTransaction().isActive()) {  
				em.getTransaction().rollback();  
			} 
		} catch (Throwable rbEx) {  
			rbEx.printStackTrace();
		    System.out.println("Could not rollback transaction after exception!");  
		}
	}

	@Override
	public void destroy() {
		jpaUtil.closeEmFactory();
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		jpaUtil = new JPAUtil();		
	}

}

