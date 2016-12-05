package br.uece.computacao.integralizaac.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;

import br.uece.computacao.integralizaac.enums.NaturezaEnum;

/**
 * @author Jocélio Otávio
 *
 * Classe que representa a entidade Atividade Complementar
 * onde são guardadas as informações das atividades de 
 * acordo com a classificação feita na resolução de 
 * atividades complementares da UECE.
 *
 */
@Entity
public class AtividadeComplementar extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1533546490012140293L;

	/**
	 * Campo texto obrigatório de 250 caracteres que guarda a descrição 
	 * da atividade complementar.
	 * 
	 */
	@Column(nullable=false, length=250)
	private String descricao;
	
	/**
	 * Campo obrigatório do tipo NaturezaEnum que aceita somente os 
	 * valores listados no enum @see NaturezaEnum.
	 */
	@Enumerated
	@Column(nullable=false)
	private NaturezaEnum natureza;
	
	/**
	 * Campo inteiro que guarda o máximo de horas que um aluno pode
	 * exercer para a atividade em um único período. Quando nulo
	 * significa que a atividade não tem limite máximo de horas por
	 * período.
	 */
	@Column
	private Integer maximoHorasPorPeriodo;
	
	/**
	 * Campo inteiro que guarda o máximo de horas que um aluno pode 
	 * exercer para a atividade em todo o curso. 
	 */
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
