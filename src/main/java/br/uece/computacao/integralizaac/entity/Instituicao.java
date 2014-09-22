package br.uece.computacao.integralizaac.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Instituicao extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(nullable=false, length=50, unique=true)
	private String nome;
	
	public Instituicao() {
		this(null, null);
	}
	
	public Instituicao(String nomeInstituicao) {
		this.nome = nomeInstituicao;
	}
	
	public Instituicao(Long id, String nomeInstituicao) {
		this.nome = nomeInstituicao;
		setId(id);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
