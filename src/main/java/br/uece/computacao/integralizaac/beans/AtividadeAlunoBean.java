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

/**
 * @author Jocélio Otávio
 *
 * Classe responsável pela camada Controller da aplicação
 * especificamente na tela de cadastro de Atividade do aluno.
 */
@ManagedBean
@ViewScoped
public class AtividadeAlunoBean <T> extends AbstractBean implements Serializable {

	/**
	 * Código que define a opçao "Outra" do combo de listagem
	 * de instituições.
	 */
	private static final long CODIGO_OUTRA_INSTITUICAO = 999999l;

	private static final long serialVersionUID = -8541357128043522063L;
	
	/**
	 * Atributo que guarda os dados da atividade que está sendo
	 * incluída, alterada ou excluída.
	 */
	protected AtividadeAluno atividadeAluno;
	
	/**
	 * Flag para identificar a situação em que o usuário está
	 * alterando uma atividade.
	 */
	protected boolean atualizando;
	
	// Dependências
	protected AtividadeComplementarBO ativComplementarBO;
	protected AtividadeAlunoBO atividadeAlunoBO;	
	protected InstituicaoBO instituicaoBO;	
	
	/**
	 * Atributo que referencia o aluno que está logado no sistema.
	 * Se não for um aluno o atributo é nulo.
	 */
	@ManagedProperty("#{loginBean.usuario.aluno}")
	protected Aluno aluno;
	
	/**
	 * Atributo que guarda a letra que identifica a natureza
	 * da atividade.
	 */
	protected String letraNatureza;
	
	/**
	 * Atributo que guarda a natureza da atividade.  
	 */
	protected NaturezaEnum natureza;
	
	/**
	 * Atributo que guarda o id da atividade do aluno, quando vindo
	 * da tela de dashboard, ou seja, é uma edição ou exclusão. 
	 */
	protected Long idAtividadeAluno;
	
	/**
	 * Atributo que guarda o nome da instituição, quando o aluno
	 * seleciona a opção "Outra" no combo de Instituições.  
	 */
	protected String nomeInstituicao;
	
	/**
	 * Objeto da classe responsável por gerenciar os certificados. 
	 */
	protected CertificadoUtils certificadoUtils;

	/**
	 * Lista com todas as instituições cadastrados no banco.
	 */
	private List<Instituicao> listaTodasInstituicoes;
	
	/**
	 * Atributo que guarda a informação se a atividade já foi
	 * aprovada. 
	 */
	private boolean atividadeAprovada;
	
	
	public AtividadeAlunoBean() {
		ativComplementarBO = new AtividadeComplementarBO(new AtividadeComplementarDao());
		atividadeAlunoBO = new AtividadeAlunoBO(new AtividadeAlunoDao());
		instituicaoBO = new InstituicaoBO(new InstituicaoDao());
	}
	
	/**
	 * Método de inicialização
	 */
	@PostConstruct
	public void init() {
		clean();
		certificadoUtils = new CertificadoUtils(aluno);
		load();
	}
	
    /**
     * Método de finalização que remove os certificados do 
     * diretório temp do servidor.
     */
    @PreDestroy
    public void destroy() {
    	if (atividadeAluno.getCertificados() != null 
    			&& !atividadeAluno.getCertificados().isEmpty()) {
    		
    		for (Certificado certificado : atividadeAluno.getCertificados()) {
    			certificadoUtils.removerCertificadoDaTemp(certificado);
			}
    	}
    }

	/**
	 * Método chamado no carregamento da tela de atividade do aluno.
	 * Se vier da tela de dashboard irá carregar os dados da atividade
	 * com Id igual ao da requisição. Faz também as devidas validações
	 * se o aluno é mesmo o aluno da atividade que está sendo carregada.
	 */
	public void load() {
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
	
	/**
	 * Método que limpa os dados da tela de atividade
	 */
	public void clean() {
		atividadeAluno = new AtividadeAluno();
		atividadeAluno.setAluno(aluno);		
		atualizando = false;
	};
	
	/**
	 * Método de inclusão de atividades do aluno.
	 */
	public void incluir() {
		if (atividadeAluno.getCertificados() == null || atividadeAluno.getCertificados().size() == 0) {
			addErrorMessageKey("atividade_aluno.certificadoObrigatorio");
			return;
		}
		
		tratarNovaInstituicao();
		
		atividadeAlunoBO.incluir(atividadeAluno);
		
		addInfoMessage("atividade_aluno.inclusao");
		
		if (atividadeAlunoBO.excedeuHorasAtividade(atividadeAluno)) {
			addAlertMessage("atividade_aluno.excedeuHoras");
		}
		
		clean();
	}

	/**
	 * Método que faz o tratamento adequado para a nova instituição
	 * que por ventura tenha sido informada no cadastro.
	 */
	private void tratarNovaInstituicao() {
		if (isOutraInstituicao() && nomeInstituicao != null) {
			Instituicao instituicao = new Instituicao(nomeInstituicao);
			instituicaoBO.incluir(instituicao);
			listaTodasInstituicoes = null;
			atividadeAluno.setInstituicao(instituicao);
		}
	}
	
	/**
	 * Méotodo de alteração.
	 */
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
	
	/**
	 * Método de exclusão.
	 */
	public void excluir() {
		atividadeAlunoBO.excluir(atividadeAluno);
		addInfoMessage("atividade_aluno.exclusao");		
		clean();
	}
	
	/**
	 * Método que faz o redirecionamento para a tela de dashboard.
	 * @return
	 */
	public String voltar() {
		String url = "/pages/dashboard.xhtml?faces-redirect=true&includeViewParams=true";
		if (atividadeAluno.getAluno() != null) {
			url += "&idAluno=" + atividadeAluno.getAluno().getId();
		}
		
		return url;
	}	
	
	/**
	 * Método que trata o upload de certificados.
	 * 
	 * @param event Evento do primefaces que encapsula o arquivo
	 * que foi enviado.
	 * 
	 * @throws IOException
	 */
	public void handleFileUpload(FileUploadEvent event) throws IOException {
		try {
			certificadoUtils.handleFileUpload(atividadeAluno, event);
		} catch (BusinessException e) {
			addErrorMessageValue(e.getMessage());
		}
	}
	
	/**
	 * Méotdo que printa o certificado na resposta da requisição.
	 * 
	 * @param certificado Certificado que deve ser exibido.
	 * @throws Exception
	 */
	public void exibirCertificado(Certificado certificado) throws Exception {
		certificadoUtils.downloadCertificado(certificado);
	}
	
	/**
	 * Méotodo que remove o certificado da lista de certificados
	 * da atividade corrente.
	 * 
	 * @param certificado Certificado a ser excluído.
	 */
	public void removerCertificado(Certificado certificado) {
		atividadeAluno.getCertificados().remove(certificado);
	}
	
	/**
	 * Méotod que retorna a lista de todas as instituições
	 * cadastradas, incluindo a opção "Outra", fazendo o 
	 * cache dessa lista através do atributo listaTodasInstituicoes.
	 * 
	 * @return Lista de todas as intituições cadastradas.
	 */
	public List<Instituicao> getListaTodasInstituicoes() {
		if (listaTodasInstituicoes == null) {
			listaTodasInstituicoes = instituicaoBO.listarTodos();
			listaTodasInstituicoes.add(new Instituicao(CODIGO_OUTRA_INSTITUICAO, "Outra..."));
		}
		return listaTodasInstituicoes;		
	}
	
	/**
	 * Método chamado no componente autocomplete de seleção de
	 * atividade complementar.
	 * 
	 * @param query Todo ou parte da descrição da atividade complementar.
	 * 
	 * @return Lista de atividades complementares filtrada.
	 */
	public List<AtividadeComplementar> completeAtividadeComplementar(String query) {
		List<AtividadeComplementar> retorno;
		
		retorno = ativComplementarBO.buscarComDescricao(natureza, query);
		
		return retorno; 
	}
	
	/**
	 * Méotodo que retorna <code>true</code> se o aluno
	 * escolheu a opção "Outra"
	 *  
	 * @return
	 */
	public boolean isOutraInstituicao() {
		return atividadeAluno.getInstituicao() != null && atividadeAluno.getInstituicao().getId() == CODIGO_OUTRA_INSTITUICAO; 
	}
	
	// ################# Getters and Setters #####################
	
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
