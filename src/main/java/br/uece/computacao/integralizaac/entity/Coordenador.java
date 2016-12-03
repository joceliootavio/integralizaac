package br.uece.computacao.integralizaac.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

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
	 * Campo texto obrigatório de 10 caracteres que guarda a matrícula
	 * do Coordenador. Não aceita duplicidade.
	 */
	@Column(nullable=false, unique=true, length=10)	
	private String matricula;
	
	/**
	 * Referência a classe @see Curso.
	 */
	@ManyToOne
	private Curso curso;	
	
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

}
