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

@ManagedBean
@ViewScoped
public class PeriodoBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -8541357128043522063L;
	
	PeriodoBO periodoBusiness;
	
	private boolean atualizando;
	
	private Periodo periodo;
	private List<Periodo> listaPeriodos;
	
	/* Dominios */
	private List<Periodo> listaTodosPeriodos;
	
	//@PostConstruct
	public PeriodoBean() {
		periodoBusiness = new PeriodoBO(new PeriodoDao());
		listaPeriodos = periodoBusiness.listarTodosPeriodos();
		clean();
	}
	
	public void clean() {
		periodo = new Periodo();
		atualizando = false;
	}
	
	public void incluir() {
		periodoBusiness.incluir(periodo);
		listaPeriodos = periodoBusiness.listarTodosPeriodos();
		clean();
		addInfoMessage("padrao.inclusao");
	}
	
	public void atualizar() {
		periodoBusiness.atualizar(periodo);
		clean();
		addInfoMessage("padrao.alteracao");
	}
	
	public void excluir() {
		periodoBusiness.excluir(periodo);
		listaPeriodos = periodoBusiness.listarTodosPeriodos();
		clean();
		addInfoMessage("padrao.exclusao");		
	}
	
	public void selecionarPeriodo(SelectEvent evento) {
		this.atualizando = true;
		this.periodo = (Periodo) evento.getObject();
	}

	public List<Periodo> getListaTodosPeriodos() {
		if (listaTodosPeriodos == null) {
			listaTodosPeriodos = periodoBusiness.listarTodosPeriodos();
			Collections.sort(listaTodosPeriodos);
		}
		return listaTodosPeriodos;
	}

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
