package br.uece.computacao.integralizaac.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import br.uece.computacao.integralizaac.enums.PerfilEnum;

/**
 * @author Jocélio Otávio
 * 
 * Classe que representa a entididade Usuário do sistema.
 * Os atributos aluno e coordenador são mutuamente
 * exclusivos, ou seja, não deve haver usuário que seja
 * ao mesmo tempo Aluno e Coordenador.
 * 
 */
@Entity
@NamedQueries({@NamedQuery(name=Usuario.FIND_ALL_COORDENADOR, query="select u from Usuario as u where u.perfil = br.uece.computacao.integralizaac.enums.PerfilEnum.Coordenador"),
				@NamedQuery(name=Usuario.FIND_ALL_ALUNO, query="select u from Usuario as u where u.perfil = br.uece.computacao.integralizaac.enums.PerfilEnum.Aluno")})
public class Usuario extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7846878949581706495L;
	
	public static final String FIND_ALL_COORDENADOR = "findAllCoordenador";
	public static final String FIND_ALL_ALUNO = "findAllAluno";	

	/**
	 * Campo texto obrigatório de 30 caracteres que guarda o login do
	 * usuário. Para o perfil Coordenador guarda o email, para o perfil Aluno
	 * guarda a matricula e para o perfil Administrador guarda o nome do
	 * usuário. 
	 */
	@Column(nullable=false, length=30)
	private String login;
	
	/**
	 * Campo texto obrigatório de 50 caracteres que guarda o 
	 * nome do usuário.
	 */
	@Column(nullable=false, length=50)
	private String nome;	
	
	/**
	 * Campo texto obrigatório de 30 caracteres que guarda
	 * o e-mail do usuário. Esse campo só deve aceitar emails
	 * institucionais. 
	 */
	@Column(nullable=false, length=30)
	private String email;	
	
	/**
	 * Campo texto obrigatório de 32 caracteres que guarda a senha
	 * criptografada do usuário. A senha inicial é gerada automaticamente
	 * pelo sistema.
	 */
	@Column(nullable=false, length=32)
	private String senha;
	
	/**
	 * Campo booleano que guarda a informação se o usuário está ativo.
	 */
	@Column
	private boolean ativo;
	
	/**
	 * Campo do tipo PerfilEnum que aceita somente os perfis
	 * listados no enum @see PerfilEnum.
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable=false, updatable=false)
	private PerfilEnum perfil;
	
	/**
	 * Referência a classe @see Aluno.
	 */
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Aluno aluno;
	
	/**
	 * Referência a class @see Coordenador.
	 */
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Coordenador coordenador;
	
	public Usuario() {
		this(null);
	}

	public Usuario(PerfilEnum perfil) {
		this.perfil = perfil;
		
		if (perfil == PerfilEnum.Coordenador){
			coordenador = new Coordenador();
		} else if (perfil == PerfilEnum.Aluno){
			aluno = new Aluno();
		}
		
		ativo = true;
	}
	
	public Curso getCurso() {
		if (perfil == PerfilEnum.Coordenador){
			return coordenador.getCurso();
		} else if (perfil == PerfilEnum.Aluno){
			return aluno.getCurso();
		}
		
		return null;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public PerfilEnum getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilEnum perfil) {
		this.perfil = perfil;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Coordenador getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(Coordenador coordenador) {
		this.coordenador = coordenador;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
