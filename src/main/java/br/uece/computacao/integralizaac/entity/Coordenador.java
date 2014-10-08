package br.uece.computacao.integralizaac.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author Jocélio Otávio
 *
 * Classe que representa a entidade Coordenador
 * do curso.
 * 
 */
@Entity
public class Coordenador extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2732379358518872292L;

	/**
	 * Campo texto obrigatório com 50 caracteres que guarda o nome 
	 * do Coordenador. 
	 */
	@Column(nullable=false, length=50)	
	private String nome;
	
	/**
	 * Campo texto obrigatório de 10 caracteres que guarda a matrícula
	 * do Coordenador. Não aceita duplicidade.
	 */
	@Column(nullable=false, unique=true, length=10)	
	private String matricula;
	
	/**
	 * Campo texto obrigatório de 30 caracteres que guarda o email
	 * institucional do Coordenador.
	 */
	@Column(nullable=false, unique=true, length=30)
	private String email;
	
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
	
}
