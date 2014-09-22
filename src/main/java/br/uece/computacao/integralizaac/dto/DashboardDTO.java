package br.uece.computacao.integralizaac.dto;


public class DashboardDTO {
	private Integer ordem;
	private Long idAtividade;
	private String atividade;
	private Long idAtividadeComplementar;
	private String atividadeComplementar;
	private String instituicao;
	private String periodo;	
	private Integer cargaHoraria;
	private Boolean status;
	private Integer cargaHorariaAproveitada;	
	private Integer maxHorasPeriodo;
	private Integer maxHorasCurso;
	private String tipoParticipacao;
	private String nomeEvento;
	
	public Integer getOrdem() {
		return ordem;
	}
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
	public Long getIdAtividade() {
		return idAtividade;
	}
	public void setIdAtividade(Long idAtividade) {
		this.idAtividade = idAtividade;
	}
	public String getAtividade() {
		return atividade;
	}
	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}
	public Long getIdAtividadeComplementar() {
		return idAtividadeComplementar;
	}
	public void setIdAtividadeComplementar(Long idAtividadeComplementar) {
		this.idAtividadeComplementar = idAtividadeComplementar;
	}
	public String getAtividadeComplementar() {
		return atividadeComplementar;
	}
	public void setAtividadeComplementar(String atividadeComplementar) {
		this.atividadeComplementar = atividadeComplementar;
	}
	public String getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public Integer getCargaHoraria() {
		return cargaHoraria;
	}
	public void setCargaHoraria(Integer cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}
	public Integer getCargaHorariaAproveitada() {
		return cargaHorariaAproveitada;
	}
	public void setCargaHorariaAproveitada(Integer cargaHorariaAproveitada) {
		this.cargaHorariaAproveitada = cargaHorariaAproveitada;
	}
	public Integer getMaxHorasPeriodo() {
		return maxHorasPeriodo;
	}
	public void setMaxHorasPeriodo(Integer maxHorasPeriodo) {
		this.maxHorasPeriodo = maxHorasPeriodo;
	}
	public Integer getMaxHorasCurso() {
		return maxHorasCurso;
	}
	public void setMaxHorasCurso(Integer maxHorasCurso) {
		this.maxHorasCurso = maxHorasCurso;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Boolean getStatus() {
		return status;
	}
	public String getTipoParticipacao() {
		return tipoParticipacao;
	}
	public void setTipoParticipacao(String tipoParticipacao) {
		this.tipoParticipacao = tipoParticipacao;
	}
	public String getNomeEvento() {
		return nomeEvento;
	}
	public void setNomeEvento(String nomeEvento) {
		this.nomeEvento = nomeEvento;
	}
	
	public String getClasseCssStatus() {
		if (status == null) {
			return "";
		} else if (status) {
			return "fonteAzul";
		} else {
			return "fonteVermelha";
		}
	}
}
