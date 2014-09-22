package br.uece.computacao.integralizaac.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.codec.digest.DigestUtils;

import br.uece.computacao.integralizaac.business.UsuarioBO;
import br.uece.computacao.integralizaac.dao.UsuarioDao;
import br.uece.computacao.integralizaac.entity.Periodo;
import br.uece.computacao.integralizaac.entity.Usuario;
import br.uece.computacao.integralizaac.enums.PerfilEnum;
import br.uece.computacao.integralizaac.services.EmailService;

@ManagedBean
@ViewScoped
public class DadosCadastraisBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -8541357128043522063L;
	
	UsuarioBO usuarioBusiness;
	
	@ManagedProperty("#{loginBean.usuario}")
	private Usuario usuario;
	
	private String nome;
	private String matricula;
	private String email;
	private Periodo periodo;
	private boolean atualizaSenha;
	private String senhaAtual;
	private String novaSenha;

	
	public DadosCadastraisBean() {
		usuarioBusiness = new UsuarioBO(new UsuarioDao(), new EmailService());
	}

	@PostConstruct	
	public void init() {
		if (usuario.getPerfil() == PerfilEnum.Aluno) {
			nome = usuario.getAluno().getNome();
			matricula = usuario.getAluno().getMatricula();
			email = usuario.getAluno().getEmail();
			periodo = usuario.getAluno().getPeriodo();
		} else if (usuario.getPerfil() == PerfilEnum.Professor) {
			nome = usuario.getProfessor().getNome();
			matricula = usuario.getProfessor().getMatricula();
			email = usuario.getProfessor().getEmail();
		}		
	}
	
	public String load() {
		return "/pages/dados_cadastrais.xhtml?faces-redirect=true";
	}
	
	public String cancelar() {
		return "/pages/main.xhtml?faces-redirect=true";
	}
	
	public void salvar() {
		if (usuario.getPerfil() == PerfilEnum.Aluno) {
			usuario.setLogin(matricula);
			usuario.getAluno().setNome(nome);
			usuario.getAluno().setMatricula(matricula);
			usuario.getAluno().setEmail(email);
			usuario.getAluno().setPeriodo(periodo);
		} else if (usuario.getPerfil() == PerfilEnum.Professor) {
			usuario.setLogin(email);
			usuario.getProfessor().setNome(nome);
			usuario.getProfessor().setMatricula(matricula);
			usuario.getProfessor().setEmail(email);
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
