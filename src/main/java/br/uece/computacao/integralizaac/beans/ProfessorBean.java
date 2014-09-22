package br.uece.computacao.integralizaac.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import br.uece.computacao.integralizaac.business.UsuarioBO;
import br.uece.computacao.integralizaac.dao.UsuarioDao;
import br.uece.computacao.integralizaac.entity.Professor;
import br.uece.computacao.integralizaac.entity.Usuario;
import br.uece.computacao.integralizaac.enums.PerfilEnum;
import br.uece.computacao.integralizaac.services.EmailService;

@ManagedBean
@ViewScoped
public class ProfessorBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -8541357128043522063L;
	
	UsuarioBO usuarioBusiness;
	
	private boolean atualizando;
	
	private Usuario usuario;
	private Professor professor;	
	private List<Usuario> listaProfessores;
	
	//@PostConstruct
	public ProfessorBean() {
		usuarioBusiness = new UsuarioBO(new UsuarioDao(), new EmailService());
		listaProfessores = usuarioBusiness.listarTodosProfessores();
		clean();
	}
	
	public String load() {
		return "/pages/professor.xhtml?faces-redirect=true";
	}
	
	public void clean() {
		usuario = new Usuario(PerfilEnum.Professor);
		professor = usuario.getProfessor();
		atualizando = false;
		
		cleanSubmittedValues("professorForm");
	}
	
	public void incluir() {
		usuarioBusiness.cadastrarProfessor(professor);
		listaProfessores = usuarioBusiness.listarTodosProfessores();
		
		addInfoMessage("atividade_aluno.inclusao");
		clean();
	}
	
	public void atualizar() {
		try {
			usuarioBusiness.atualizar(usuario);
			
			addInfoMessage("atividade_aluno.alteracao");
		} catch (RuntimeException e) {
			listaProfessores = usuarioBusiness.listarTodosProfessores();
			throw e;
		}
		
		clean();
	}
	
	public void excluir() {
		usuarioBusiness.excluir(usuario);
		listaProfessores = usuarioBusiness.listarTodosProfessores();
		
		addInfoMessage("atividade_aluno.exclusao");
		
		clean();
	}
	
	public void selecionarProfessor(SelectEvent evento) {
		this.atualizando = true;
		this.usuario = (Usuario) evento.getObject();
		this.professor = usuario.getProfessor();
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public List<Usuario> getListaProfessores() {
		return listaProfessores;
	}

	public boolean isAtualizando() {
		return atualizando;
	}
	
}
