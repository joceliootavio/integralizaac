package br.uece.computacao.integralizaac.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.codec.digest.DigestUtils;

import br.uece.computacao.integralizaac.business.UsuarioBO;
import br.uece.computacao.integralizaac.dao.UsuarioDao;
import br.uece.computacao.integralizaac.entity.Periodo;
import br.uece.computacao.integralizaac.entity.Usuario;
import br.uece.computacao.integralizaac.enums.FormaIngressoEnum;
import br.uece.computacao.integralizaac.enums.PerfilEnum;
import br.uece.computacao.integralizaac.services.EmailService;

/**
 * @author Jocélio Otávio
 *
 * Classe responsável pela camada Controller da aplicação
 * especificamente na tela de cadastro de Dados Cadastrais.
 */
@ManagedBean
@ViewScoped
public class DadosCadastraisBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -8541357128043522063L;
	
	UsuarioBO usuarioBusiness;
	
	/**
	 * Atributo que guarda referencia ao usuário logado.
	 */
	@ManagedProperty("#{loginBean.usuario}")
	private Usuario usuario;
	
	// Atributos que guardam os dados corrente do usuário.
	private String nome;
	private String matricula;
	private String email;
	private FormaIngressoEnum formaIngresso;	
	private Periodo periodo;
	private boolean atualizaSenha;
	private String senhaAtual;
	private String novaSenha;

	
	public DadosCadastraisBean() {
		usuarioBusiness = new UsuarioBO(new UsuarioDao(), new EmailService());
	}

	/**
	 * Método de inicialização da tela, onde são carregados
	 * os dados do usuário logado, seja aluno ou coordenador.
	 */
	@PostConstruct	
	public void init() {
		if (usuario.getPerfil() == PerfilEnum.Aluno) {
			nome = usuario.getAluno().getNome();
			matricula = usuario.getAluno().getMatricula();
			email = usuario.getAluno().getEmail();
			periodo = usuario.getAluno().getPeriodo();
			formaIngresso = usuario.getAluno().getFormaIngresso();			
		} else if (usuario.getPerfil() == PerfilEnum.Coordenador) {
			nome = usuario.getCoordenador().getNome();
			matricula = usuario.getCoordenador().getMatricula();
			email = usuario.getCoordenador().getEmail();
		}		
	}
	
	/**
	 * Método chamado ao carregar a tela de dados cadastrais
	 * 
	 * @return Url que redireciona para tela de dados cadastrais.
	 */
	public String load() {
		return "/pages/dados_cadastrais.xhtml?faces-redirect=true";
	}
	
	/**
	 * Método que redireciona pra tela principal do sistema
	 * ao cancelar a operação.
	 * 
	 * @return Url que redireciona para tela principal.
	 */	
	public String cancelar() {
		return "/pages/main.xhtml?faces-redirect=true";
	}
	
	/**
	 * Método que salva os dados alterados pelo usuário.
	 */
	public void salvar() {
		if (usuario.getPerfil() == PerfilEnum.Aluno) {
			usuario.setLogin(matricula);
			usuario.getAluno().setNome(nome);
			usuario.getAluno().setMatricula(matricula);
			usuario.getAluno().setEmail(email);
			usuario.getAluno().setPeriodo(periodo);
			usuario.getAluno().setFormaIngresso(formaIngresso);			
		} else if (usuario.getPerfil() == PerfilEnum.Coordenador) {
			usuario.setLogin(email);
			usuario.getCoordenador().setNome(nome);
			usuario.getCoordenador().setMatricula(matricula);
			usuario.getCoordenador().setEmail(email);
		}
		
		if (atualizaSenha) {
			if (!DigestUtils.md5Hex(senhaAtual).equals(usuario.getSenha())) {
				addErrorMessageValue("Senha atual não confere.");
				return;
			}
			
			usuario.setSenha(DigestUtils.md5Hex(novaSenha));
		}
			
		usuarioBusiness.atualizar(usuario);
		
		addInfoMessage("dados_cadastrais.alteracao");
	}
	
	/**
	 * Método que retorna todas as formas de ingresso cadastradas
	 * de acordo com enum FormaIngressoEnum.
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
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public FormaIngressoEnum getFormaIngresso() {
		return formaIngresso;
	}

	public void setFormaIngresso(FormaIngressoEnum formaIngresso) {
		this.formaIngresso = formaIngresso;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public boolean isAtualizaSenha() {
		return atualizaSenha;
	}

	public void setAtualizaSenha(boolean atualizaSenha) {
		this.atualizaSenha = atualizaSenha;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

}
