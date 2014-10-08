package br.uece.computacao.integralizaac.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Jocélio Otávio
 *
 * Classe que representa os períodos que compõem o calendário 
 * da Universidade.
 */
@Entity
public class Periodo extends BaseEntity implements Comparable<Periodo>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Campo texto obrigatório de 6 caracteres contendo o nome
	 * do período obedecendo a notação 9999.9 utilizada pela
	 * Universidade. Não aceita duplicidade.
	 */
	@Column(nullable=false, length=6, unique=true)
	private String nome;
	
	/**
	 * Campo data que guarda a data onde se inicia o período.
	 */
	@Temporal(TemporalType.DATE)
	private Date dataInicio;
	
	/**
	 * Campo data contendo a data em que termina o período.
	 */
	@Temporal(TemporalType.DATE)
	private Date dataFim;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataInicio() {
		return dataInicio;
	}
	
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataFim = dataTermino;
	}

	@Override
	public int compareTo(Periodo o) {
    	return o.getNome().compareTo(this.getNome());
	}
	
}
