package br.uece.computacao.integralizaac.beans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import br.uece.computacao.integralizaac.business.AtividadeComplementarBO;
import br.uece.computacao.integralizaac.dao.AtividadeComplementarDao;
import br.uece.computacao.integralizaac.entity.AtividadeComplementar;
import br.uece.computacao.integralizaac.entity.Usuario;
import br.uece.computacao.integralizaac.enums.NaturezaEnum;

@ManagedBean
@ViewScoped
public class AtividadeComplementarBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -8541357128043522063L;
	
	private AtividadeComplementarBO atividadeComplemenarBO;
	
	private List<AtividadeComplementar> listaAtividades;
	
	@ManagedProperty("#{loginBean.usuario}")
	private Usuario usuario;
	
	private AtividadeComplementar atividade;
	private boolean atualizando = false;

	private List<NaturezaEnum> listaTodasNaturezas;
	
	public AtividadeComplementarBean() {
		atividadeComplemenarBO = new AtividadeComplementarBO(new AtividadeComplementarDao());
		listaAtividades = atividadeComplemenarBO.listarTodasAtividades();
		clean();		
	}
	
	public void clean() {
		atividade = new AtividadeComplementar();
		atualizando = false;
	}
	
	public void incluir() {
		atividadeComplemenarBO.incluir(atividade);
		listaAtividades = atividadeComplemenarBO.listarTodasAtividades();
		clean();
		addInfoMessage("padrao.inclusao");
	}
	
	public void atualizar() {
		atividadeComplemenarBO.atualizar(atividade);
		clean();
		addInfoMessage("padrao.alteracao");
	}
	
	public void excluir() {
		atividadeComplemenarBO.excluir(atividade);
		listaAtividades = atividadeComplemenarBO.listarTodasAtividades();
		clean();
		addInfoMessage("padrao.exclusao");		
	}
	
	public void selecionarAtividade(SelectEvent evento) {
		this.atualizando = true;
		this.atividade = (AtividadeComplementar) evento.getObject();
	}
	
	public List<NaturezaEnum> getListaTodasNaturezas() {
		if (listaTodasNaturezas == null) {
			listaTodasNaturezas = Arrays.asList(NaturezaEnum.values());
		}
		return listaTodasNaturezas;
	}

	public List<AtividadeComplementar> getListaAtividades() {
		return listaAtividades;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isAtualizando() {
		return atualizando;
	}

	public AtividadeComplementar getAtividade() {
		return atividade;
	}

}
