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

/**
 * @author Jocélio Otávio
 *
 * Classe responsável pela camada Controller da aplicação
 * especificamente na tela de cadastro de Atividades Complementares.
 */
@ManagedBean
@ViewScoped
public class AtividadeComplementarBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -8541357128043522063L;
	
	private AtividadeComplementarBO atividadeComplemenarBO;
	
	/**
	 * Lista com as atividades complementares cadastradas.
	 */
	private List<AtividadeComplementar> listaAtividades;
	
	/**
	 * Atributo que guarda referencia ao usuário logado.
	 */
	@ManagedProperty("#{loginBean.usuario}")
	private Usuario usuario;
	
	/**
	 * Atributo que guarda a atividade complementar que está
	 * sendo incluída, alterada ou excluída.
	 */
	private AtividadeComplementar atividade;
	
	/**
	 * Flag que identifica quando o usuário está alterando ou
	 * excluíndo uma atividade complementar. 
	 */
	private boolean atualizando = false;

	/**
	 * Lista com todas as naturezas de atividade cadastradas.
	 */
	private List<NaturezaEnum> listaTodasNaturezas;
	
	public AtividadeComplementarBean() {
		atividadeComplemenarBO = new AtividadeComplementarBO(new AtividadeComplementarDao());
		listaAtividades = atividadeComplemenarBO.listarTodasAtividades();
		clean();		
	}
	
	/**
	 * Méotodo que limpa a tela de cadastro de Atividades complementares.
	 */
	public void clean() {
		atividade = new AtividadeComplementar();
		atualizando = false;
	}
	
	/**
	 * Méotodo de inclusão da entidade AtividadeComplementar.
	 */
	public void incluir() {
		atividadeComplemenarBO.incluir(atividade);
		listaAtividades = atividadeComplemenarBO.listarTodasAtividades();
		clean();
		addInfoMessage("padrao.inclusao");
	}
	
	/**
	 * Método de alteração da entidade AtividadeComplementar.
	 */
	public void atualizar() {
		atividadeComplemenarBO.atualizar(atividade);
		clean();
		addInfoMessage("padrao.alteracao");
	}
	
	/**
	 * Método de exclusão da entidade AtividadeComplementar.
	 */
	public void excluir() {
		atividadeComplemenarBO.excluir(atividade);
		listaAtividades = atividadeComplemenarBO.listarTodasAtividades();
		clean();
		addInfoMessage("padrao.exclusao");		
	}
	
	/**
	 * Método chamado ao selecionar uma atividade complementar na 
	 * tabela de atividades complementares.
	 * 
	 * @param evento Evento que acionou o método.
	 */
	public void selecionarAtividade(SelectEvent evento) {
		this.atualizando = true;
		this.atividade = (AtividadeComplementar) evento.getObject();
	}
	
	/**
	 * Método que retorna a lista de todas as naturezas
	 * de acordo com o enum @see NaturezaEnum.
	 * 
	 * @return Lista de naturezas de atividade.
	 */
	public List<NaturezaEnum> getListaTodasNaturezas() {
		if (listaTodasNaturezas == null) {
			listaTodasNaturezas = Arrays.asList(NaturezaEnum.values());
		}
		return listaTodasNaturezas;
	}
	
	// ################# Getters and Setters #####################	

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
