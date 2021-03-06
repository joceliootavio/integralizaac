package br.uece.computacao.integralizaac.entity;

import java.util.Date;

import javax.persistence.*;

import br.uece.computacao.integralizaac.enums.CentroEnum;

/**
 * @author Jocélio Otávio
 *
 * Classe que representa os períodos que compõem o calendário 
 * da Universidade.
 */
@Entity
public class Curso extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Campo texto obrigatório de 10 caracteres contendo o código
	 * do curso. Não aceita duplicidade.
	 */
	@Column(nullable=false, length=10, unique=true)
	private String codigo;

	/**
	 * Campo texto obrigatório de 50 caracteres contendo o nome
	 * do curso. Não aceita duplicidade.
	 */
	@Column(nullable=false, length=50, unique=true)
	private String nome;
	
	/**
	 * Campo data que guarda a data de implantação do curso.
	 */
	@Temporal(TemporalType.DATE)
	private Date dataImplantacao;
	
	/**
	 * Campo data contendo a data de encerramento do curso.
	 */
	@Temporal(TemporalType.DATE)
	private Date dataEncerramento;
	
	/**
	 * Campo obrigatório do tipo CentroEnum que aceita somente os 
	 * valores listados no enum @see CentroEnum.
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private CentroEnum centro;	
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataImplantacao() {
		return dataImplantacao;
	}

	public void setDataImplantacao(Date dataImplantacao) {
		this.dataImplantacao = dataImplantacao;
	}

	public Date getDataEncerramento() {
		return dataEncerramento;
	}

	public void setDataEncerramento(Date dataEncerramento) {
		this.dataEncerramento = dataEncerramento;
	}

	public CentroEnum getCentro() {
		return centro;
	}

	public void setCentro(CentroEnum centro) {
		this.centro = centro;
	}

}
