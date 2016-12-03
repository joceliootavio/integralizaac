package br.uece.computacao.integralizaac.beans;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.uece.computacao.integralizaac.business.AtividadeAlunoBO;
import br.uece.computacao.integralizaac.business.ParecerAtividadeAlunoBO;
import br.uece.computacao.integralizaac.business.UsuarioBO;
import br.uece.computacao.integralizaac.dao.AtividadeAlunoDao;
import br.uece.computacao.integralizaac.dao.ParecerAtividadeAlunoDao;
import br.uece.computacao.integralizaac.dao.UsuarioDao;
import br.uece.computacao.integralizaac.dto.DashboardDTO;
import br.uece.computacao.integralizaac.dto.ListaDashboard;
import br.uece.computacao.integralizaac.entity.Aluno;
import br.uece.computacao.integralizaac.entity.AtividadeAluno;
import br.uece.computacao.integralizaac.entity.ParecerAtividadeAluno;
import br.uece.computacao.integralizaac.entity.Usuario;
import br.uece.computacao.integralizaac.enums.NaturezaEnum;
import br.uece.computacao.integralizaac.enums.PerfilEnum;
import br.uece.computacao.integralizaac.reports.RelatorioVO;
import br.uece.computacao.integralizaac.services.EmailService;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author Jocélio Otávio
 *
 * Classe responsável pela camada Controller da aplicação
 * especificamente na tela de Dashboard.
 */
@ManagedBean
@ViewScoped
public class DashboardBean extends AbstractBean implements Serializable {

	/**
	 * Total de horas correspondente a 1(um) crédito.
	 */
	private static final int HORAS_CREDITO = 17;

	private static final long serialVersionUID = -8541357128043522063L;
	
	private AtividadeAlunoBO atividadeAlunoBO;
	private ParecerAtividadeAlunoBO parecerAtividadeAlunoBO;	
	private UsuarioBO usuarioBO;
	private JasperPrint jasperPrint; 
	
	/**
	 * Atributo que guarda referencia ao usuário logado.
	 */	
	@ManagedProperty("#{loginBean.usuario}")
	private Usuario usuario;
	
	/**
	 * Atributo que guarda o aluno selecionado pelo coordenador
	 * ou quando o usuário for do perfil aluno, o próprio aluno
	 * logado.
	 */
	private Aluno aluno;
	
	/**
	 * Lista de naturezas na qual o aluno tem atividades 
	 * cadastradas
	 */
	private List<NaturezaEnum> listaNaturezas;
	
	/**
	 * Mapa de lista de DTOs que representam as atividades 
	 * complementares do aluno tendo como chave a natureza
	 * da atividade.  
	 */
	private Map<NaturezaEnum, ListaDashboard> mapaAtividades;
	
	/**
	 * Soma total de horas de todas as atividades do aluno,
	 * desconsiderando as horas de atividades já reprovadas.
	 */
	private int somaTotalHoras;
	
	/**
	 * Atividade selecionada para edição ou inclusão de parecer
	 * pelo coordenador. 
	 */
	private AtividadeAluno atividadeAlunoSelecionada;
	
	/**
	 * Atributo que guarda os dados de observações do parecer
	 * corrente. 
	 */
	private String observacaoParecer;
	
	/**
	 * Id do aluno selecionado ou logado no sistema. 
	 */
	private Long idAluno;

	/**
	 * Flag para identificar o cenário de alteração do parecer. 
	 */
	private boolean editandoParecer;

	public DashboardBean() {
		atividadeAlunoBO = new AtividadeAlunoBO(new AtividadeAlunoDao());
		
		EmailService emailService = new EmailService();
		usuarioBO = new UsuarioBO(new UsuarioDao(), emailService);
		parecerAtividadeAlunoBO = new ParecerAtividadeAlunoBO(new ParecerAtividadeAlunoDao(), emailService, usuarioBO);
		
		mapaAtividades = new LinkedHashMap<NaturezaEnum, ListaDashboard>();
		somaTotalHoras = 0;
	}

	/**
	 * Método de inicialização da tela de Dashboard que carrega
	 * imediatamente os dados de atividades do aluno quando esse
	 * está logado no sistema.
	 */
	@PostConstruct
	public void init() {
		if (usuario.getPerfil() == PerfilEnum.Aluno) {
			aluno = usuario.getAluno();
			selecionarAluno(aluno);
		}
		if (usuario.getPerfil() != PerfilEnum.Aluno && aluno != null) {
			selecionarAluno(aluno);
		}
	}
	
	/**
	 * Método chamado ao selecionar um aluno na lista de alunos
	 * exibida.
	 * 
	 * @param event Evento que originou a chamada.
	 */
	public void autocompleteSelect(SelectEvent event) {
		selecionarAluno((Aluno) event.getObject());
	}
	
	/**
	 * Seleciona o aluno limpando antes as informações de
	 * atividades do aluno selecionado anteriormente.
	 * 
	 * @param aluno Aluno selecionado.
	 */
	public void selecionarAluno(Aluno aluno) {
		limparTabelaAtividades();
		
		listaNaturezas = atividadeAlunoBO.listarNaturezas(aluno);
	}

	/**
	 * Limpar tabela de atividades do aluno.
	 */
	private void limparTabelaAtividades() {
		mapaAtividades = new LinkedHashMap<NaturezaEnum, ListaDashboard>();
		somaTotalHoras = 0;
	}
	
	/**
	 * Método chamado ao selecionar uma determinada atividade 
	 * do aluno.
	 * 
	 * @param dashboardDTO DTO que representa a atividade do aluno.
	 */
	public void selecionarAtividadeAluno(DashboardDTO dashboardDTO) {
		atividadeAlunoSelecionada = atividadeAlunoBO.buscaPorId(dashboardDTO.getIdAtividade());
		observacaoParecer = null;
		editandoParecer = false;
	}

	/**
	 * Método que inclui o parecer de aprovação utilizando
	 * os dados correntes do parecer.
	 */
	public void aprovarAtividadeAluno() {
		incluirParecer(true);
	}
	
	/**
	 * Método que inclui o parecer de reprovação utilizando
	 * os dados correntes do parecer.
	 */
	public void reprovarAtividadeAluno() {
		incluirParecer(false);
	}

	/**
	 * Método de inclusão de parecer com os dados da atividade
	 * selecionada e as observações corrente.
	 * 
	 * @param aprovado Flag se aprovado ou reprovado.
	 */
	private void incluirParecer(boolean aprovado) {
		ParecerAtividadeAluno parecer = new ParecerAtividadeAluno();
		
		parecer.setAtividadeAluno(atividadeAlunoSelecionada);
		parecer.setAprovado(aprovado);
		parecer.setCoordenador(usuario.getCoordenador());
		parecer.setObservacao(observacaoParecer);
		
		parecerAtividadeAlunoBO.incluir(parecer);
		
		atividadeAlunoSelecionada.setParecer(parecer);
		limparTabelaAtividades();
		observacaoParecer = null;
		addInfoMessage("parecer.msgSucesso");
	}
	
	/**
	 * Méotodo que prepara a alteração do parecer de uma atividade.
	 */
	public void prepareEditarParecer() {
		editandoParecer = true;
	}
	
	/**
	 * Méotodo que retorna <code>true</code> quando é permitido
	 * editar um parecer, verificando se o coordenador que está
	 * consultando o parecer é o próprio que o cadastrou.
	 * 
	 * @return
	 */
	public boolean isPodeEditarParecer() {
		return atividadeAlunoSelecionada.getParecer() != null
					&& atividadeAlunoSelecionada.getParecer().getCoordenador().equals(usuario.getCoordenador());
	}

	/**
	 * Méotodo de alteração do parecer.
	 */
	public void editarParecer() {
		parecerAtividadeAlunoBO.atualizar(atividadeAlunoSelecionada.getParecer());
		addInfoMessage("parecer.msgSucesso");
	}
	
	/**
	 * Méotodo de exclusão do parecer.
	 */	
	public void excluirParecer() {
		parecerAtividadeAlunoBO.excluir(atividadeAlunoSelecionada.getParecer());
		atividadeAlunoSelecionada.setParecer(null);
		limparTabelaAtividades();
		addInfoMessage("padrao.exclusao");		
	}
	
	/**
	 * Método de cancelamento de alteração ou exclusão do 
	 * parecer da atividade selecionada.
	 */
	public void cancelEditarParecer() {
		editandoParecer = false;
	}
	
	/**
	 * Atividades do aluno da natureza passada como parâmetro.
	 * 
	 * @param natureza Natureza da atividade
	 * 
	 * @return Lista de DTOs que representam as atividades do aluno.
	 */
	public ListaDashboard atividadesPorNatureza(NaturezaEnum natureza) {
		if (mapaAtividades.get(natureza) == null) {
			mapaAtividades.put(natureza, atividadeAlunoBO.listarAtividadesPorNatureza(aluno, natureza));
			somaTotalHoras += mapaAtividades.get(natureza).getSomaTotalHoras();
		}
		return mapaAtividades.get(natureza);
		
	}

	/**
	 * Método que retorna o total de créditos correspondentes as
	 * horas de atividades cadastradas pelo aluno.
	 * 
	 * @return Total de créditos cursados pelo aluno.
	 */
	public Long getTotalCreditosCorrespondentes() {
		if (listaNaturezas != null && !listaNaturezas.isEmpty()) {
			return new Double(Math.floor(((double) somaTotalHoras) / HORAS_CREDITO)).longValue();
		} else {
			return 0l;
		}
	}
	
	/**
	 * Méotodo que envia solicitação de avaliação
	 * feita pelo aluno para os coordenadores do curso, 
	 * quando esse aluno houver atingido o mínimo obrigatório
	 * para colação.
	 */
	public void solicitarAvaliacao() {
		int horasExigidas = Integer.parseInt(getResourcesProvider().getValue("horasExigidasCurso"));
		
		if (somaTotalHoras < horasExigidas) {
			RequestContext.getCurrentInstance().addCallbackParam("mensagem", msgUtil.getMessage("dashboard.msgHorasExigidas"));
		} else {
			usuarioBO.avisarCoordenadoresAvaliacao(usuario);
			RequestContext.getCurrentInstance().addCallbackParam("mensagem", msgUtil.getMessage("dashboard.msgSolicitacaoAvaliacao"));
		}
	}

	/**
	 * Méotodo que gera o mapa de registro de atividades em pdf
	 * passando os parâmetros para o jasper gerá-lo.
	 * 
	 * @return Objeto contendo os dados do pdf.
	 * @throws IOException
	 */
	public StreamedContent getRelatorioAtividades() throws IOException {
		ByteArrayOutputStream outputBytes = new ByteArrayOutputStream();
		InputStream relatorio = null;
		
		try {
			List<RelatorioVO> listaRelatorio = new ArrayList<RelatorioVO>();
			RelatorioVO relatorioVO = null;
			
			int rowCount = 0;
			for (Entry<NaturezaEnum, ListaDashboard> entry : mapaAtividades.entrySet()) {
				relatorioVO = new RelatorioVO(rowCount);
				relatorioVO.setNatureza(entry.getKey());
				relatorioVO.addDtos(entry.getValue());
				
				if (relatorioVO.getSomaHorasNatureza() > 0) {
					rowCount = relatorioVO.getCountDto();
					listaRelatorio.add(relatorioVO);
				}
			}
			
			Map<String, Object> mapaParametros = new HashMap<String, Object>();
			mapaParametros.put("CENTRO_FACULDADE", "Centro de Ciências e Tecnologia");
			mapaParametros.put("CURSO", "Ciências da Computação");
			mapaParametros.put("ALUNO", usuario.getNome());
			mapaParametros.put("MATRICULA", aluno.getMatricula());			
			mapaParametros.put("PERIODO_INGRESSO", aluno.getPeriodo().getNome());			
			mapaParametros.put("FORMA_INGRESSO", aluno.getFormaIngresso().name());
			mapaParametros.put("TOTAL_HORAS", somaTotalHoras);
			mapaParametros.put("TOTAL_CREDITOS", getTotalCreditosCorrespondentes());
			
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listaRelatorio, true);
			//String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/relatorios/quadro_atividades.jrxml");
			String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/relatorios/quadro_atividades.jasper");
			
			//jasperPrint = JasperFillManager.fillReport(JasperCompileManager.compileReport(reportPath), mapaParametros, beanCollectionDataSource);
			jasperPrint = JasperFillManager.fillReport(reportPath, mapaParametros, beanCollectionDataSource);			

			JasperExportManager.exportReportToPdfStream(jasperPrint, outputBytes);
			relatorio = new ByteArrayInputStream(outputBytes.toByteArray());
		} catch(Exception ex) {
			ex.printStackTrace();
			addErrorMessageValue(ex.getMessage());
		} finally {
			jasperPrint = null;			
			outputBytes.close();
		}
		
		return new DefaultStreamedContent(relatorio, "application/pdf", "relatorio_atividades.pdf");
	}
	
	// ########		Getters and Setters		###########

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getSomaTotalHoras() {
		return somaTotalHoras;
	}

	public AtividadeAluno getAtividadeAlunoSelecionada() {
		return atividadeAlunoSelecionada;
	}

	public String getObservacaoParecer() {
		return observacaoParecer;
	}

	public void setObservacaoParecer(String observacaoParecer) {
		this.observacaoParecer = observacaoParecer;
	}

	public boolean isEditandoParecer() {
		return editandoParecer;
	}
	
	public Long getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(Long idAluno) {
		this.idAluno = idAluno;
	}

	public List<NaturezaEnum> getListaNaturezas() {
		return listaNaturezas;
	}

}
