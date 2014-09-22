package br.uece.computacao.integralizaac.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Periodo extends BaseEntity implements Comparable<Periodo>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(nullable=false, length=6, unique=true)
	private String nome;
	
	@Temporal(TemporalType.DATE)
	private Date dataInicio;
	
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
