package br.uece.computacao.integralizaac.beans;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import br.uece.computacao.integralizaac.business.PeriodoBO;
import br.uece.computacao.integralizaac.dao.PeriodoDao;
import br.uece.computacao.integralizaac.entity.Periodo;

/**
 * @author Jocélio Otávio
 *
 * Classe responsável pela camada Controller da aplicação
 * especificamente na tela de cadastro de Periodo.
 */
@ManagedBean
@ViewScoped
public class PeriodoBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -8541357128043522063L;
	
	private PeriodoBO periodoBusiness;
	
	/**
	 * Flag para identificar a situação em que o usuário está
	 * alterando uma atividade.
	 */	
	private boolean atualizando;
	
	/**
	 * Atributo que guarda os dados do período que será incluído,
	 * alterado ou excluído.
	 */
	private Periodo periodo;
	
	/**
	 * Lista dos períodos já cadastrados.
	 */
	private List<Periodo> listaPeriodos;
	
	/* Dominios */
	/**
	 * Lista todos os períodos para exibição nos combos
	 * de período da aplicação.
	 */
	private List<Periodo> listaTodosPeriodos;
	
	//@PostConstruct
	public PeriodoBean() {
		periodoBusiness = new PeriodoBO(new PeriodoDao());
		listaPeriodos = periodoBusiness.listarTodosPeriodos();
		clean();
	}
	
	/**
	 * Método que limpa os dados da tela de período.
	 */
	public void clean() {
		periodo = new Periodo();
		atualizando = false;
	}
	
	/**
	 * Método de inclusão de períodos.
	 */
	public void incluir() {
		periodoBusiness.incluir(periodo);
		listaPeriodos = periodoBusiness.listarTodosPeriodos();
		clean();
		addInfoMessage("padrao.inclusao");
	}
	
	/**
	 * Método de alteração de períodos.
	 */	
	public void atualizar() {
		periodoBusiness.atualizar(periodo);
		clean();
		addInfoMessage("padrao.alteracao");
	}
	
	/**
	 * Método de exclusão de períodos.
	 */	
	public void excluir() {
		periodoBusiness.excluir(periodo);
		listaPeriodos = periodoBusiness.listarTodosPeriodos();
		clean();
		addInfoMessage("padrao.exclusao");		
	}
	
	/**
	 * Méotodo chamado ao selecionar um período.
	 * 
	 * @param evento Evento que originou a chamada. 
	 */
	public void selecionarPeriodo(SelectEvent evento) {
		this.atualizando = true;
		this.periodo = (Periodo) evento.getObject();
	}

	/**
	 * Retorna a lista e toos os períodos cadastrados.
	 * @return
	 */
	public List<Periodo> getListaTodosPeriodos() {
		if (listaTodosPeriodos == null) {
			listaTodosPeriodos = periodoBusiness.listarTodosPeriodos();
			Collections.sort(listaTodosPeriodos);
		}
		return listaTodosPeriodos;
	}
	
	// ################# Getters and Setters #####################	

	public List<Periodo> getListaPeriodos() {
		return listaPeriodos;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public boolean isAtualizando() {
		return atualizando;
	}

}
