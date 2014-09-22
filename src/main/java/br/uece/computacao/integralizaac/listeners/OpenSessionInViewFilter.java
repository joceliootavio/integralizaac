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
  
            throw new ServletException(ex);  
        } finally {
            if (em != null) {
            	em.close();
            }
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

