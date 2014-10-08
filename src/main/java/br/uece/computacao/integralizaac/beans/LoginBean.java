package br.uece.computacao.integralizaac.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.uece.computacao.integralizaac.business.UsuarioBO;
import br.uece.computacao.integralizaac.dao.UsuarioDao;
import br.uece.computacao.integralizaac.entity.Usuario;
import br.uece.computacao.integralizaac.enums.PerfilEnum;
import br.uece.computacao.integralizaac.services.EmailService;

/**
 * @author Jocélio Otávio
 *
 * Classe responsável pela camada Controller da aplicação
 * especificamente na tela de acesso ao sistema. Uma instância
 * dessa classe é compartilhada por toda a sessão do usuário.
 */
@ManagedBean
@SessionScoped
public class LoginBean extends AbstractBean {
	
	/**
	 * Atributo que guarda o login informado pelo usuário
	 * para acesso ao sistema.
	 */
	private String login;
	
	/**
	 * Atributo que guarda a senha informada pelo usuário
	 * na tela de login. 
	 */
	private String senha;
	
	/**
	 * Atributo que guarda o perfil do usuário que realizou
	 * o login.
	 */
	private PerfilEnum perfil;
	
	/**
	 * Atributo que guarda os dados do usuário logado no sistema. 
	 */
	private Usuario usuario;
	
	private UsuarioBO usuarioBO;
	
	@PostConstruct
	public void init() {
		usuarioBO = new UsuarioBO(new UsuarioDao(), new EmailService());
	}
	
	/**
	 * Méotodo que efetua o login do sistema realizando a
	 * autenticação do usuário através da senha.
	 * 
	 * @return Url para a pagina que deve ser redirecionado.
	 */
	public String efetuarLogin() {
		usuario = usuarioBO.efetuarLogin(login, senha);
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute("usuarioLogado", usuario);
		
		if (usuario.getPerfil() == PerfilEnum.Administrador) {
			return "/pages/main.xhtml?faces-redirect=true";
		} else {
			return "/pages/dashboard.xhtml?faces-redirect=true";			
		}
	}
	
	/**
	 * Méotodo chamado ao sair do sistema. Redireciona para a 
	 * tela de login do sistema.
	 * 
	 * @return Url da página de login do sistema.
	 */
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/pages/login.xhtml?faces-redirect=true";
	}
	
	/**
	 * Método que retorna um array com todos os perfis 
	 * de acordo com o enum PerfilEnum.
	 * 
	 * @return Array com os perfis do usuário.
	 */
	public PerfilEnum[] getListaTodosPerfis() {
		return PerfilEnum.values();
	}
	
	/**
	 * Retorna <code>true</code> quando o perfil do 
	 * usuário é o de aluno.
	 * 
	 * @return
	 */
	public boolean isPerfilAluno() {
		return usuario != null && usuario.getPerfil() == PerfilEnum.Aluno;
	}

	/**
	 * Retorna <code>true</code> quando o perfil do 
	 * usuário é o de coordenador.
	 * 
	 * @return
	 */	
	public boolean isPerfilCoordenador() {
		return usuario != null 
				&& usuario.getPerfil() == PerfilEnum.Coordenador;
	}
	
	/**
	 * Retorna <code>true</code> quando o perfil do 
	 * usuário é o de administrador.
	 * 
	 * @return
	 */	
	public boolean isPerfilAdministrador() {
		return usuario != null 
				&& usuario.getPerfil() == PerfilEnum.Administrador;
	}	
	
	// ################# Getters and Setters #####################	
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public PerfilEnum getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilEnum perfil) {
		this.perfil = perfil;
	}

	public Usuario getUsuario() {
		return usuario;
	}

}
