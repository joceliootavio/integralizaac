package br.uece.computacao.integralizaac.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ParecerAtividadeAluno extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6693071577698468336L;
	
	@OneToOne(optional=false)
	private AtividadeAluno atividadeAluno;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	private Professor professor;
	
	@Column(nullable=false)
	private Boolean aprovado;
	
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(columnDefinition="TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	private Date dataHoraCadastro; 

	@Column(length=1000)
	private String observacao;

	public AtividadeAluno getAtividadeAluno() {
		return atividadeAluno;
	}

	public void setAtividadeAluno(AtividadeAluno atividadeAluno) {
		this.atividadeAluno = atividadeAluno;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public boolean isAprovado() {
		return aprovado;
	}

	public void setAprovado(boolean aprovado) {
		this.aprovado = aprovado;
	}

	public Date getDataHoraCadastro() {
		return dataHoraCadastro;
	}

	public void setDataHoraCadastro(Date dataHoraCadastro) {
		this.dataHoraCadastro = dataHoraCadastro;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
}
