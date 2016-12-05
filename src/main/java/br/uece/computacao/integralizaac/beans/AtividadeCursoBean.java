package br.uece.computacao.integralizaac.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.UnselectEvent;

import br.uece.computacao.integralizaac.business.AtividadeComplementarBO;
import br.uece.computacao.integralizaac.business.AtividadeCursoBO;
import br.uece.computacao.integralizaac.dao.AtividadeComplementarDao;
import br.uece.computacao.integralizaac.dao.AtividadeCursoDao;
import br.uece.computacao.integralizaac.entity.AtividadeComplementar;
import br.uece.computacao.integralizaac.entity.Curso;

/**
 * @author Jocélio Otávio
 *
 * Classe responsável pela camada Controller da aplicação
 * especificamente na tela de cadastro de Atividades Curso.
 */
@ManagedBean
@ViewScoped
public class AtividadeCursoBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -8541357128043522063L;
	
	private AtividadeComplementarBO atividadeComplemenarBO;
	
	private AtividadeCursoBO atividadeCursoBO;
	
	/**
	 * Atributo que guarda referencia ao usuário logado.
	 */	
	@ManagedProperty("#{loginBean.usuario.coordenador.curso}")
	private Curso cursoDoCoordenador;
	
	/**
	 * Lista com as atividades complementares cadastradas.
	 */
	private List<AtividadeComplementar> listaAtividades;
	
	/**
	 * Lista com as atividades complementares selecionadas.
	 */
	private List<AtividadeComplementar> listaAtividadesSelecionadas;	
	
	public AtividadeCursoBean() {
		atividadeComplemenarBO = new AtividadeComplementarBO(new AtividadeComplementarDao());
		atividadeCursoBO = new AtividadeCursoBO(new AtividadeCursoDao());
		
		listaAtividades = atividadeComplemenarBO.listarTodasAtividades();
	}
	
	public void unselectAtividade(UnselectEvent evento) {
		AtividadeComplementar atividadeDeselecionada = (AtividadeComplementar) evento.getObject();
		
		if (atividadeComplemenarBO.verificaAtividadeUtilizada(atividadeDeselecionada.getId())) {
			addErrorMessageValue("Atividade \"" + atividadeDeselecionada.getDescricao()+ "\" já foi utilizada por um aluno portanto não pode ser removida.");
			FacesContext.getCurrentInstance().validationFailed();
			listaAtividadesSelecionadas.add(atividadeDeselecionada);
		}
	}

	public void atualizar() {
		atividadeCursoBO.atualizar(cursoDoCoordenador, listaAtividadesSelecionadas);
		
		addInfoMessage("atividade_curso.alteracao");
	}
	
	// ################# Getters and Setters #####################	

	public List<AtividadeComplementar> getListaAtividades() {
		return listaAtividades;
	}

	public List<AtividadeComplementar> getListaAtividadesSelecionadas() {
		if (listaAtividadesSelecionadas == null) {
			listaAtividadesSelecionadas = atividadeCursoBO.listaAtividadesCurso(cursoDoCoordenador.getId());
		}
		return listaAtividadesSelecionadas;
	}

	public void setListaAtividadesSelecionadas(List<AtividadeComplementar> listaAtividadesSelecionadas) {
		this.listaAtividadesSelecionadas = listaAtividadesSelecionadas;
	}

	public void setCursoDoCoordenador(Curso cursoDoCoordenador) {
		this.cursoDoCoordenador = cursoDoCoordenador;
	}

}
