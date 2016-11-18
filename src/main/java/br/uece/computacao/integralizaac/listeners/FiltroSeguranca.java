package br.uece.computacao.integralizaac.listeners;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.uece.computacao.integralizaac.entity.Usuario;

/**
 * @author Jocélio Otávio
 *
 * Filtro responsável por verificar se o usuário está
 * logado no sistema. Se não estiver logado, o usuário
 * será redirecionado para a tela de login.
 *
 */
public class FiltroSeguranca implements Filter {

	private static final String LOGIN_PAGE = "/pages/login.jsf";
	
	private static final String[] SESSIONLESS_PAGES = {LOGIN_PAGE, "/pages/aluno.jsf", "/pages/reset-senha.jsf"};	

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

		if (usuario == null 
				&& !verificaPaginasNoLogin(request.getRequestURL())) {
			session.setAttribute("msg", "Você não está logado no sistema!");
			((HttpServletResponse) res).sendRedirect(request.getContextPath() + LOGIN_PAGE);
		} else {
			chain.doFilter(req, res);
		}

	}
	
	private boolean verificaPaginasNoLogin(StringBuffer requestUrl) {
		for (String url : SESSIONLESS_PAGES) {
			if (requestUrl.indexOf(url) != -1)
				return true;
		}
		
		return false;
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
