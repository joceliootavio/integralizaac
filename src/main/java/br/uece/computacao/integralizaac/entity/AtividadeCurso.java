package br.uece.computacao.integralizaac.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Jocélio Otávio
 *
 * Classe que representa o relacionamento entre as entidades
 * Aluno e AtividadeComplementar, e onde será guardada as 
 * informações das atividades exercidas por um determinado 
 * aluno.
 * 
 */
@Inheritance(strategy=InheritanceType.JOINED)
@Entity
public class AtividadeCurso extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * Referência obrigatória à classe Aluno.
	 */
	@ManyToOne(optional=false)
	private Curso curso;
	
	/**
	 * Referência obrigatória à classe @see AtividadeComplementar.
	 */
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	private AtividadeComplementar atividade;
	
	/**
	 * Campo alimentado automaticamente pelo banco de dados com 
	 * a Data e hora do momento em que a atividade do aluno foi 
	 * cadastrada.
	 */
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(columnDefinition="TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	private Date dataHoraCadastro;
	
	private boolean ativo = true;

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public AtividadeComplementar getAtividade() {
		return atividade;
	}

	public void setAtividade(AtividadeComplementar atividade) {
		this.atividade = atividade;
	}

	public Date getDataHoraCadastro() {
		return dataHoraCadastro;
	}

	public void setDataHoraCadastro(Date dataHoraCadastro) {
		this.dataHoraCadastro = dataHoraCadastro;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
}
