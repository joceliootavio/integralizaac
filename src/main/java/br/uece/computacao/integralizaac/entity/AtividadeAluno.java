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

@Inheritance(strategy=InheritanceType.JOINED)
@Entity
public class AtividadeAluno extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional=false)
	private Aluno aluno;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	private AtividadeComplementar atividade;
	
	@OneToOne(mappedBy="atividadeAluno")
	private ParecerAtividadeAluno parecer;
	
	@ManyToOne(optional=false)
	private Periodo periodo;
	
	@ManyToOne
	private Instituicao instituicao;	
	
	@Column
	private Integer cargaHoraria;
	
	@Column(length=50, nullable=true)
	private String nomeEvento;	
	
	@Column(length=50, nullable=false)
	private String descricao;
	
	@Column(length=20, nullable=true)
	private String tipoParticipacao;	
	
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(columnDefinition="TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	private Date dataHoraCadastro;
	
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
