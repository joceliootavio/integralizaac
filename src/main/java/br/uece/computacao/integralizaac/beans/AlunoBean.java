package br.uece.computacao.integralizaac.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.uece.computacao.integralizaac.business.UsuarioBO;
import br.uece.computacao.integralizaac.dao.UsuarioDao;
import br.uece.computacao.integralizaac.entity.Aluno;
import br.uece.computacao.integralizaac.entity.Usuario;
import br.uece.computacao.integralizaac.enums.FormaIngressoEnum;
import br.uece.computacao.integralizaac.services.EmailService;

/**
 * @author Jocélio Otávio
 *
 * Classe responsável pela camada Controller da aplicação
 * especificamente na tela de cadastro de Aluno.
 */
@ManagedBean
@ViewScoped
public class AlunoBean extends AbstractBean implements Serializable {

	/**
	 * Url a página de login do sistema.
	 */
	private static final String LOGIN_PAGE = "/pages/login.jsf?faces-redirect=true";

	private static final long serialVersionUID = -8541357128043522063L;
	
	/**
	 * Objeto da classe de negócio da entidade Usuario.
	 */
	private UsuarioBO usuarioBO;
	
	/**
	 * Atributo que deve guardar os dados do aluno a ser
	 * cadastrado.
	 */
	private Aluno aluno;

	/**
	 * Lista de todos os alunos cadastrados na base de dados.
	 */
	private List<Usuario> listaAlunos;
	
	public AlunoBean() {
		usuarioBO = new UsuarioBO(new UsuarioDao(), new EmailService());
		aluno = new Aluno();
	}
	
	/**
	 * Método que inicializa a lista de alunos.
	 */
	public void initLista() {
		listaAlunos = usuarioBO.listarTodosAlunos();
	}
	
	/**
	 * Método de cancelamento da tela de cadastro do
	 * aluno. Redireciona para tela de login.
	 * 
	 * @return Url da tela de login.
	 */
	public String cancelar() {
		return LOGIN_PAGE;
	}
	
	/**
	 * Método que realiza o cadastro do aluno, ou se houver
	 * erro, adiciona mensagem de erro na tela.
	 */
	public void cadastrar() {
		try {
			usuarioBO.cadastrarAluno(aluno);
		} catch (Exception e) {
			addErrorMessageValue(e.getMessage());
			FacesContext.getCurrentInstance().validationFailed();
		}
	}
	
	/**
	 * Método chamado no componente autocomplete de seleção de
	 * aluno.
	 * 
	 * @param query Todo ou parte da matrícula ou nome do aluno.
	 * 
	 * @return Lista de alunos filtrada.
	 */
	public List<Aluno> completeAluno(String query) {
		List<Aluno> retorno;
		
		retorno = usuarioBO.buscarAlunosComMatriculaOuNome(query);
		
		return retorno; 
	}
	
	/**
	 * Método que redefine a senha do usuário passado como
	 * parâmetro.
	 * 
	 * @param usuario Usuário
	 */
	public void resetarSenha(Usuario usuario) {
		usuarioBO.resetarSenhaAluno(usuario);
		
		addInfoMessage("aluno.resetSenhaAluno");
	}
	
	/**
	 * Méotdo que retorna a lista de todas as formas de 
	 * ingresso de acordo com o enum @see FormaIngressoEnum
	 *  
	 * @return Lista de formas de ingresso.
	 */
	public List<FormaIngressoEnum> getListaTodasFormasIngresso() {
		List<FormaIngressoEnum> retorno = new ArrayList<FormaIngressoEnum>();
		
		for (FormaIngressoEnum formaIngresso : FormaIngressoEnum.values()) {
			retorno.add(formaIngresso);
		}
		
		return retorno;
	}
	
	// ################# Getters and Setters #####################

	public Aluno getAluno() {
		return aluno;
	}

	public List<Usuario> getListaAlunos() {
		return listaAlunos;
	}

}
