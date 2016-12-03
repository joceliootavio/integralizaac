package br.uece.computacao.integralizaac.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import br.uece.computacao.integralizaac.business.CursoBO;
import br.uece.computacao.integralizaac.dao.CursoDao;
import br.uece.computacao.integralizaac.entity.Curso;

/**
 * @author Jocélio Otávio
 *
 * Classe responsável pela camada Controller da aplicação
 * especificamente na tela de cadastro de Curso.
 */
@ManagedBean
@ViewScoped
public class CursoBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -8541357128043522063L;
	
	private CursoBO cursoBusiness;
	
	/**
	 * Flag para identificar a situação em que o usuário está
	 * alterando uma atividade.
	 */	
	private boolean atualizando;
	
	/**
	 * Atributo que guarda os dados do período que será incluído,
	 * alterado ou excluído.
	 */
	private Curso periodo;
	
	/**
	 * Lista dos períodos já cadastrados.
	 */
	private List<Curso> listaCursos;
	
	//@PostConstruct
	public CursoBean() {
		cursoBusiness = new CursoBO(new CursoDao());
		listaCursos = cursoBusiness.listarTodos();
		clean();
	}
	
	/**
	 * Método que limpa os dados da tela de período.
	 */
	public void clean() {
		periodo = new Curso();
		atualizando = false;
	}
	
	/**
	 * Método de inclusão de períodos.
	 */
	public void incluir() {
		cursoBusiness.incluir(periodo);
		listaCursos = cursoBusiness.listarTodos();
		clean();
		addInfoMessage("padrao.inclusao");
	}
	
	/**
	 * Método de alteração de períodos.
	 */	
	public void atualizar() {
		cursoBusiness.atualizar(periodo);
		clean();
		addInfoMessage("padrao.alteracao");
	}
	
	/**
	 * Método de exclusão de períodos.
	 */	
	public void excluir() {
		cursoBusiness.excluir(periodo);
		listaCursos = cursoBusiness.listarTodos();
		clean();
		addInfoMessage("padrao.exclusao");		
	}
	
	/**
	 * Méotodo chamado ao selecionar um período.
	 * 
	 * @param evento Evento que originou a chamada. 
	 */
	public void selecionarCurso(SelectEvent evento) {
		this.atualizando = true;
		this.periodo = (Curso) evento.getObject();
	}
	
	// ################# Getters and Setters #####################	

	public List<Curso> getListaCursos() {
		return listaCursos;
	}

	public Curso getCurso() {
		return periodo;
	}

	public boolean isAtualizando() {
		return atualizando;
	}

}
