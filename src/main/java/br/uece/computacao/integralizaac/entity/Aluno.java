package br.uece.computacao.integralizaac.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Aluno extends BaseEntity {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 8217078288149747337L;

	@Column(nullable=false)
	private String nome;
	
	@Column(nullable=false, unique=true, length=8)
	private String matricula;
	
	@ManyToOne(optional=false)
	private Periodo periodo;
	
	@Column(nullable=true, length=30)
	private String email;

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
