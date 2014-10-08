package br.uece.computacao.integralizaac.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
public class AtividadeAluno extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * Referência obrigatória à classe Aluno.
	 */
	@ManyToOne(optional=false)
	private Aluno aluno;
	
	/**
	 * Referência obrigatória à classe @see AtividadeComplementar.
	 */
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	private AtividadeComplementar atividade;
	
	/**
	 * Referência a classe @see ParecerAtividadeAluno.
	 */
	@OneToOne(mappedBy="atividadeAluno", cascade=CascadeType.REMOVE)
	private ParecerAtividadeAluno parecer;
	
	/**
	 * Referência obrigatória a classe @see Periodo que representa o 
	 * periodo em que a atividade foi exercida pelo Aluno.
	 */
	@ManyToOne(optional=false)
	private Periodo periodo;
	
	/**
	 * Referência a classe @see Instituicao.
	 */
	@ManyToOne
	private Instituicao instituicao;	
	
	/**
	 * Campo inteiro obrigatório que guarda a carga horária da atividade
	 * exercida pelo aluno.
	 */
	@Column(nullable=false)
	private Integer cargaHoraria;
	
	/**
	 * Campo texto com 50 caracteres que guarda o nome do evento no qual
	 * o aluno exerceu a atividade
	 */
	@Column(length=50, nullable=true)
	private String nomeEvento;	
	
	/**
	 * Campo texto obrigatório com 50 caracteres que guarda a descrição
	 * para identificar a atividade exercida pelo Aluno.
	 */
	@Column(length=50, nullable=false)
	private String descricao;
	
	/**
	 * Campo texto de 20 caracteres que guarda o tipo de participação do
	 * aluno em atividades que seja necessária essa informação.
	 */
	@Column(length=20, nullable=true)
	private String tipoParticipacao;	
	
	/**
	 * Campo alimentado automaticamente pelo banco de dados com 
	 * a Data e hora do momento em que a atividade do aluno foi 
	 * cadastrada.
	 */
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(columnDefinition="TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	private Date dataHoraCadastro;
	
	/**
	 * Referência aos certificados da atividade exercida pelo Aluno. @see Certificado
	 */
	@OneToMany(cascade=CascadeType.ALL, mappedBy="atividadeAluno", orphanRemoval=true)
	private List<Certificado> certificados = new ArrayList<Certificado>();
	
	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public AtividadeComplementar getAtividade() {
		return atividade;
	}

	public void setAtividade(AtividadeComplementar atividade) {
		this.atividade = atividade;
	}

	public ParecerAtividadeAluno getParecer() {
		return parecer;
	}

	public void setParecer(ParecerAtividadeAluno parecer) {
		this.parecer = parecer;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public Integer getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(Integer cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public String getNomeEvento() {
		return nomeEvento;
	}

	public void setNomeEvento(String nomeEvento) {
		this.nomeEvento = nomeEvento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipoParticipacao() {
		return tipoParticipacao;
	}

	public void setTipoParticipacao(String tipoParticipacao) {
		this.tipoParticipacao = tipoParticipacao;
	}

	public List<Certificado> getCertificados() {
		return certificados;
	}

	public void setCertificados(List<Certificado> certificados) {
		this.certificados = certificados;
	}

	public Date getDataHoraCadastro() {
		return dataHoraCadastro;
	}

}
