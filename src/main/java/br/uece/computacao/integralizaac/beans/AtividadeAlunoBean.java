package br.uece.computacao.integralizaac.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;

import br.uece.computacao.integralizaac.business.AtividadeAlunoBO;
import br.uece.computacao.integralizaac.business.AtividadeComplementarBO;
import br.uece.computacao.integralizaac.business.InstituicaoBO;
import br.uece.computacao.integralizaac.dao.AtividadeAlunoDao;
import br.uece.computacao.integralizaac.dao.AtividadeComplementarDao;
import br.uece.computacao.integralizaac.dao.InstituicaoDao;
import br.uece.computacao.integralizaac.entity.Aluno;
import br.uece.computacao.integralizaac.entity.AtividadeAluno;
import br.uece.computacao.integralizaac.entity.AtividadeComplementar;
import br.uece.computacao.integralizaac.entity.Certificado;
import br.uece.computacao.integralizaac.entity.Instituicao;
import br.uece.computacao.integralizaac.enums.NaturezaEnum;
import br.uece.computacao.integralizaac.exceptions.BusinessException;
import br.uece.computacao.integralizaac.utils.CertificadoUtils;

@ManagedBean
@ViewScoped
public class AtividadeAlunoBean <T> extends AbstractBean implements Serializable {

	private static final long CODIGO_OUTRA_INSTITUICAO = 999999l;

	private static final long serialVersionUID = -8541357128043522063L;
	
	protected AtividadeAluno atividadeAluno;
	
	protected boolean atualizando;
	
	// Dependências
	protected AtividadeComplementarBO ativComplementarBO;
	protected AtividadeAlunoBO atividadeAlunoBO;	
	protected InstituicaoBO instituicaoBO;	
	
	@ManagedProperty("#{loginBean.usuario.aluno}")
	protected Aluno aluno;
	
	protected String letraNatureza;
	protected NaturezaEnum natureza;
	protected Long idAtividadeAluno;
	protected String nomeInstituicao;
	protected CertificadoUtils certificadoUtils;

	private List<Instituicao> listaTodasInstituicoes;
	private boolean atividadeAprovada;
	
	
	public AtividadeAlunoBean() {
		ativComplementarBO = new AtividadeComplementarBO(new AtividadeComplementarDao());
		atividadeAlunoBO = new AtividadeAlunoBO(new AtividadeAlunoDao());
		instituicaoBO = new InstituicaoBO(new InstituicaoDao());
	}
	
	@PostConstruct
	public void init() {
		clean();
		certificadoUtils = new CertificadoUtils(aluno);
		load();
	}
	
    @PreDestroy
    public void destroy() {
    	if (atividadeAluno.getCertificados() != null 
    			&& !atividadeAluno.getCertificados().isEmpty()) {
    		
    		for (Certificado certificado : atividadeAluno.getCertificados()) {
    			certificadoUtils.removerCertificadoDaTemp(certificado);
			}
    	}
    }

	public void load() {
		//atividadeAluno.setAtividade(ativComplementarBO.buscaPorCodAtividade(codAtividadeComplementar));
		
		if (idAtividadeAluno != null && !atualizando) {
			AtividadeAluno atividadeAlunoRecuperado = null;
			
			try {			
				atividadeAlunoRecuperado = (AtividadeAluno) atividadeAlunoBO.buscaPorId((Long) idAtividadeAluno);
			} catch (ClassCastException e) {
				addErrorMessageValue("Atividade diferente do tipo informado.");
				return;
			}
			
			if (atividadeAlunoRecuperado != null) {
				if (aluno != null 
						&& (!atividadeAlunoRecuperado.getAluno().equals(aluno))) {
					addErrorMessageValue("Atividade pertence a outro aluno.");
				} else {
					this.atividadeAluno = atividadeAlunoRecuperado;
					this.natureza = atividadeAlunoRecuperado.getAtividade().getNatureza();
					atualizando = true;
					
					if (aluno == null)
						certificadoUtils = new CertificadoUtils(atividadeAluno.getAluno());
					
					if (atividadeAluno.getParecer() != null) {
						this.atividadeAprovada =  atividadeAluno.getParecer().isAprovado();
					}
				}
			}
		} else {
			this.natureza = NaturezaEnum.naturezaComLetra(letraNatureza);
		}
	}
	
	public void clean() {
		atividadeAluno = new AtividadeAluno();
		atividadeAluno.setAluno(aluno);		
		atualizando = false;
	};
	
	public void incluir() {
		if (atividadeAluno.getCertificados() == null || atividadeAluno.getCertificados().size() == 0) {
			addErrorMessageKey("atividade_aluno.certificadoObrigatorio");
			return;
		}
		
		tratarNovaInstituicao();
		
		atividadeAlunoBO.incluir(atividadeAluno);
		
		if (atividadeAlunoBO.excedeuHorasAtividade(atividadeAluno)) {
			addAlertMessage("atividade_aluno.excedeuHoras");
		}
		
		addInfoMessage("atividade_aluno.inclusao");
		
		clean();
	}

	private void tratarNovaInstituicao() {
		if (isOutraInstituicao() && nomeInstituicao != null) {
			Instituicao instituicao = new Instituicao(nomeInstituicao);
			instituicaoBO.incluir(instituicao);
			listaTodasInstituicoes = null;
			atividadeAluno.setInstituicao(instituicao);
		}
	}
	
	public void atualizar() {
		if (atividadeAluno.getCertificados() == null || atividadeAluno.getCertificados().size() == 0) {
			addErrorMessageKey("atividade_aluno.certificadoObrigatorio");
			return;
		}
		
		tratarNovaInstituicao();
		
		if (atividadeAlunoBO.excedeuHorasAtividade(atividadeAluno)) {
			addAlertMessage("atividade_aluno.excedeuHoras");
		}
		
		atividadeAlunoBO.atualizar(atividadeAluno);
		addInfoMessage("atividade_aluno.alteracao");
		
	}
	
	public void excluir() {
		atividadeAlunoBO.excluir(atividadeAluno);
		addInfoMessage("atividade_aluno.exclusao");		
		clean();
	}
	
	public String voltar() {
		String url = "/pages/dashboard.xhtml?faces-redirect=true&includeViewParams=true";
		if (atividadeAluno.getAluno() != null) {
			url += "&idAluno=" + atividadeAluno.getAluno().getId();
		}
		
		return url;
	}	
	
	public void handleFileUpload(FileUploadEvent event) throws IOException {
		try {
			certificadoUtils.handleFileUpload(atividadeAluno, event);
		} catch (BusinessException e) {
			addErrorMessageValue(e.getMessage());
		}
	}
	
	public void exibirCertificado(Certificado certificado) throws Exception {
		certificadoUtils.downloadCertificado(certificado);
	}
	
	public void removerCertificado(Certificado certificado) {
		atividadeAluno.getCertificados().remove(certificado);
	}
	
	public List<Instituicao> getListaTodasInstituicoes() {
		if (listaTodasInstituicoes == null) {
			listaTodasInstituicoes = instituicaoBO.listarTodos();
			listaTodasInstituicoes.add(new Instituicao(CODIGO_OUTRA_INSTITUICAO, "Outra..."));
		}
		return listaTodasInstituicoes;		
	}
	
	public List<AtividadeComplementar> completeAtividadeComplementar(String query) {
		List<AtividadeComplementar> retorno;
		
		retorno = ativComplementarBO.buscarComDescricao(natureza, query);
		
		return retorno; 
	}
	
	public boolean isOutraInstituicao() {
		return atividadeAluno.getInstituicao() != null && atividadeAluno.getInstituicao().getId() == CODIGO_OUTRA_INSTITUICAO; 
	}
	
	public AtividadeAluno getAtividadeAluno() {
		return atividadeAluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public boolean isAtualizando() {
		return atualizando;
	}
	
	public Long getIdAtividadeAluno() {
		return idAtividadeAluno;
	}

	public void setIdAtividadeAluno(Long idAtividadeAluno) {
		this.idAtividadeAluno = idAtividadeAluno;
	}
	
	public String getNomeInstituicao() {
		return nomeInstituicao;
	}

	public void setNomeInstituicao(String nomeInstituicao) {
		this.nomeInstituicao = nomeInstituicao;
	}

	public String getLetraNatureza() {
		return letraNatureza;
	}

	public void setLetraNatureza(String letraNatureza) {
		this.letraNatureza = letraNatureza;
	}

	public NaturezaEnum getNatureza() {
		return natureza;
	}

	public boolean isAtividadeAprovada() {
		return atividadeAprovada;
	}

}
