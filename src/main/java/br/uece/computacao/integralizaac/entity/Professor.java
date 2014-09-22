package br.uece.computacao.integralizaac.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Professor extends BaseEntity {
	
	@Column(nullable=false, length=50)	
	private String nome;
	
	@Column(nullable=false, unique=true, length=10)	
	private String matricula;
	
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
