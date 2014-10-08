package br.uece.computacao.integralizaac.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import br.uece.computacao.integralizaac.business.UsuarioBO;
import br.uece.computacao.integralizaac.dao.UsuarioDao;
import br.uece.computacao.integralizaac.entity.Coordenador;
import br.uece.computacao.integralizaac.entity.Usuario;
import br.uece.computacao.integralizaac.enums.PerfilEnum;
import br.uece.computacao.integralizaac.services.EmailService;

/**
 * @author Jocélio Otávio
 *
 * Classe responsável pela camada Controller da aplicação
 * especificamente na tela de cadastro de Coordenadores.
 */
@ManagedBean
@ViewScoped
public class CoordenadorBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -8541357128043522063L;
	
	UsuarioBO usuarioBusiness;
	
	/**
	 * Flag que identifica quando o usuário está alterando ou
	 * excluíndo uma atividade complementar. 
	 */
	private boolean atualizando;
	
	/**
	 * Atributo que guarda os dados de usuário do Coordenador 
	 * que está sendo incluído, alterado ou excluído. 
	 */
	private Usuario usuario;
	
	/**
	 * Atributo que guarda os dados do Coordenador 
	 * que está sendo incluído, alterado ou excluído. 
	 */
	private Coordenador coordenador;	
	
	/**
	 * Lista de coordenadores cadastrados. 
	 */
	private List<Usuario> listaCoordenadores;
	
	//@PostConstruct
	public CoordenadorBean() {
		usuarioBusiness = new UsuarioBO(new UsuarioDao(), new EmailService());
		listaCoordenadores = usuarioBusiness.listarTodosCoordenadores();
		clean();
	}
	
	/**
	 * Méotodo que redireciona para a página de Coordenador 
	 * @return
	 */
	public String load() {
		return "/pages/coordenador.xhtml?faces-redirect=true";
	}
	
	/**
	 * Método que limpa os dados da tela.
	 */
	public void clean() {
		usuario = new Usuario(PerfilEnum.Coordenador);
		coordenador = usuario.getCoordenador();
		atualizando = false;
		
		cleanSubmittedValues("coordenadorForm");
	}
	
	/**
	 * Método de inclusão da entidade Coordenador.
	 */
	public void incluir() {
		usuarioBusiness.cadastrarCoordenador(coordenador);
		listaCoordenadores = usuarioBusiness.listarTodosCoordenadores();
		
		addInfoMessage("atividade_aluno.inclusao");
		clean();
	}
	
	/**
	 * Método de alteração da entidade Coordenador.
	 */	
	public void atualizar() {
		try {
			usuarioBusiness.atualizar(usuario);
			
			addInfoMessage("atividade_aluno.alteracao");
		} catch (RuntimeException e) {
			listaCoordenadores = usuarioBusiness.listarTodosCoordenadores();
			throw e;
		}
		
		clean();
	}
	
	/**
	 * Método de exclusão da entidade Coordenador.
	 */	
	public void excluir() {
		usuarioBusiness.excluir(usuario);
		listaCoordenadores = usuarioBusiness.listarTodosCoordenadores();
		
		addInfoMessage("atividade_aluno.exclusao");
		
		clean();
	}
	
	/**
	 * Método chamado ao selecionar um coordenador na tabela 
	 * de listagem de coordenadores.
	 * 
	 * @param evento Evento que originou a ação.
	 */
	public void selecionarCoordenador(SelectEvent evento) {
		this.atualizando = true;
		this.usuario = (Usuario) evento.getObject();
		this.coordenador = usuario.getCoordenador();
	}
	
	// ################# Getters and Setters #####################	

	public Coordenador getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(Coordenador professor) {
		this.coordenador = professor;
	}

	public List<Usuario> getListaCoordenadores() {
		return listaCoordenadores;
	}

	public boolean isAtualizando() {
		return atualizando;
	}
	
}
