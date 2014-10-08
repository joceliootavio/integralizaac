package br.uece.computacao.integralizaac.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Jocélio Otávio
 *
 * Classe que faz o relacionamento entre as entidades @see Coordenador
 * e @see AtividadeAluno. Essa classe deve conter a informação da
 * aprovação ou não da atividade pelo Coordenador.
 */
@Entity
public class ParecerAtividadeAluno extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6693071577698468336L;
	
	/**
	 * Referência a classe @see AtividadeAluno.
	 */
	@OneToOne(optional=false)
	private AtividadeAluno atividadeAluno;
	
	/**
	 * Referência a classe @see Coordenador
	 */
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	private Coordenador coordenador;
	
	/**
	 * Campo booleano que guarda a informação se o 
	 * coordenador aprovou ou reprovou a atividade
	 * do aluno.
	 */
	@Column(nullable=false)
	private Boolean aprovado;
	
	/**
	 * Campo alimentado automaticamente pelo banco de dados com 
	 * a Data e hora do momento em que o parecer foi dado
	 * pelo Coordenador do curso.
	 */
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(columnDefinition="TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	private Date dataHoraCadastro; 

	/**
	 * Campo texto de 1000 caracteres que guarda qualquer observação
	 * que o Coordenador faça a uma determinada atividade do Aluno.
	 */
	@Column(length=1000)
	private String observacao;

	public AtividadeAluno getAtividadeAluno() {
		return atividadeAluno;
	}

	public void setAtividadeAluno(AtividadeAluno atividadeAluno) {
		this.atividadeAluno = atividadeAluno;
	}

	public Coordenador getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(Coordenador coordenador) {
		this.coordenador = coordenador;
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
