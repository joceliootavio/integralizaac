package br.uece.computacao.integralizaac.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;

import br.uece.computacao.integralizaac.enums.NaturezaEnum;

@Entity
public class AtividadeComplementar extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1533546490012140293L;

	@Column(nullable=false, length=250)
	private String descricao;
	
	@Enumerated
	@Column(nullable=false)
	private NaturezaEnum natureza;
	
	@Column
	private Integer maximoHorasPorPeriodo;
	
	@Column(nullable=false)
	private Integer maximoHorasPorCurso;

	
	/** Getters and Setters **/
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public NaturezaEnum getNatureza() {
		return natureza;
	}

	public void setNatureza(NaturezaEnum natureza) {
		this.natureza = natureza;
	}

	public Integer getMaximoHorasPorPeriodo() {
		return maximoHorasPorPeriodo;
	}

	public void setMaximoHorasPorPeriodo(Integer maximoHorasPorPeriodo) {
		if (maximoHorasPorPeriodo == 0) {
			this.maximoHorasPorPeriodo = null;
		} else {
			this.maximoHorasPorPeriodo = maximoHorasPorPeriodo;
		}
	}

	public Integer getMaximoHorasPorCurso() {
		return maximoHorasPorCurso;
	}

	public void setMaximoHorasPorCurso(Integer maximoHorasPorCurso) {
		this.maximoHorasPorCurso = maximoHorasPorCurso;
	}
	
}
