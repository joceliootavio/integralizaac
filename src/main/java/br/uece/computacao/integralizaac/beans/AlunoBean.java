package br.uece.computacao.integralizaac.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.uece.computacao.integralizaac.business.UsuarioBO;
import br.uece.computacao.integralizaac.dao.UsuarioDao;
import br.uece.computacao.integralizaac.entity.Aluno;
import br.uece.computacao.integralizaac.entity.Usuario;
import br.uece.computacao.integralizaac.services.EmailService;

@ManagedBean
@ViewScoped
public class AlunoBean extends AbstractBean implements Serializable {

	private static final String LOGIN_PAGE = "/pages/login.jsf?faces-redirect=true";

	private static final long serialVersionUID = -8541357128043522063L;
	
	private UsuarioBO usuarioBO;
	
	private Aluno aluno;

	private List<Usuario> listaAlunos;
	
	public AlunoBean() {
		usuarioBO = new UsuarioBO(new UsuarioDao(), new EmailService());
		aluno = new Aluno();
	}
	
	public void initLista() {
		listaAlunos = usuarioBO.listarTodosAlunos();
	}
	
	public String cancelar() {
		return LOGIN_PAGE;
	}
	
	public void cadastrar() {
		try {
			usuarioBO.cadastrarAluno(aluno);
		} catch (Exception e) {
			addErrorMessageValue(e.getMessage());
			FacesContext.getCurrentInstance().validationFailed();
		}
	}
	
	public List<Aluno> completeAluno(String query) {
		List<Aluno> retorno;
		
		retorno = usuarioBO.buscarAlunosComMatriculaOuNome(query);
		
		return retorno; 
	}
	
	public void resetarSenha(Usuario usuario) {
		usuarioBO.resetarSenhaAluno(usuario);
		
		addInfoMessage("aluno.resetSenhaAluno");
	}

	public Aluno getAluno() {
		return aluno;
	}

	public List<Usuario> getListaAlunos() {
		return listaAlunos;
	}

}
