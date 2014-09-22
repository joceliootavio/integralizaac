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

@ManagedBean
@SessionScoped
public class LoginBean extends AbstractBean {
	private String login;
	private String senha;
	private PerfilEnum perfil;
	private Usuario usuario;
	
	private UsuarioBO usuarioBO;
	
	@PostConstruct
	public void init() {
		usuarioBO = new UsuarioBO(new UsuarioDao(), new EmailService());
	}
	
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
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/pages/login.xhtml?faces-redirect=true";
	}
	
	public PerfilEnum[] getListaTodosPerfis() {
		return PerfilEnum.values();
	}
	
	public boolean isPerfilAluno() {
		return usuario != null && usuario.getPerfil() == PerfilEnum.Aluno;
	}

	public boolean isPerfilProfessor() {
		return usuario != null 
				&& usuario.getPerfil() == PerfilEnum.Professor;
	}
	
	public boolean isPerfilAdministrador() {
		return usuario != null 
				&& usuario.getPerfil() == PerfilEnum.Administrador;
	}	
	
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
