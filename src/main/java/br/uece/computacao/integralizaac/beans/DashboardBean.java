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

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
import br.uece.computacao.integralizaac.entity.Periodo;
import br.uece.computacao.integralizaac.entity.Usuario;
import br.uece.computacao.integralizaac.enums.NaturezaEnum;
import br.uece.computacao.integralizaac.enums.PerfilEnum;
import br.uece.computacao.integralizaac.reports.RelatorioVO;
import br.uece.computacao.integralizaac.services.EmailService;

@ManagedBean
@ViewScoped
public class DashboardBean extends AbstractBean implements Serializable {

	private static final int HORAS_CREDITO = 17;

	private static final long serialVersionUID = -8541357128043522063L;
	
	private AtividadeAlunoBO atividadeAlunoBO;
	private ParecerAtividadeAlunoBO parecerAtividadeAlunoBO;	
	private UsuarioBO usuarioBO;
	private JasperPrint jasperPrint; 
	
	@ManagedProperty("#{loginBean.usuario}")
	private Usuario usuario;
	
	/* Filtros */
	private Aluno aluno;
	private Periodo periodo;
	
	private List<NaturezaEnum> listaNaturezas;
	private Map<NaturezaEnum, ListaDashboard> mapaAtividades;
	private List<AtividadeAluno> atividadesAluno;

	private int somaTotalHoras;
	private AtividadeAluno atividadeAlunoSelecionada;
	private String observacaoParecer;
	private Long idAluno;

	private boolean editandoParecer;

	public DashboardBean() {
		atividadeAlunoBO = new AtividadeAlunoBO(new AtividadeAlunoDao());
		
		EmailService emailService = new EmailService();
		parecerAtividadeAlunoBO = new ParecerAtividadeAlunoBO(new ParecerAtividadeAlunoDao(), emailService);
		usuarioBO = new UsuarioBO(new UsuarioDao(), emailService);
		
		mapaAtividades = new LinkedHashMap<NaturezaEnum, ListaDashboard>();
		atividadesAluno = new ArrayList<AtividadeAluno>();
		periodo = null;
		somaTotalHoras = 0;
	}

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
	
	public void autocompleteSelect(SelectEvent event) {
		selecionarAluno((Aluno) event.getObject());
	}
	
	public void selecionarAluno(Aluno aluno) {
		limparTabelaAtividades();
		
		listaNaturezas = atividadeAlunoBO.listarNaturezas(aluno);
	}

	private void limparTabelaAtividades() {
		mapaAtividades = new LinkedHashMap<NaturezaEnum, ListaDashboard>();
		somaTotalHoras = 0;
	}
	
	public void selecionarAtividadeAluno(DashboardDTO dashboardDTO) {
		atividadeAlunoSelecionada = atividadeAlunoBO.buscaPorId(dashboardDTO.getIdAtividade());
		observacaoParecer = null;
		editandoParecer = false;
	}

	public void aprovarAtividadeAluno() {
		incluirParecer(true);
	}
	
	public void reprovarAtividadeAluno() {
		incluirParecer(false);
	}

	private void incluirParecer(boolean aprovado) {
		ParecerAtividadeAluno parecer = new ParecerAtividadeAluno();
		
		parecer.setAtividadeAluno(atividadeAlunoSelecionada);
		parecer.setAprovado(aprovado);
		parecer.setProfessor(usuario.getProfessor());
		parecer.setObservacao(observacaoParecer);
		
		parecerAtividadeAlunoBO.incluir(parecer);
		
		atividadeAlunoSelecionada.setParecer(parecer);
		limparTabelaAtividades();
		observacaoParecer = null;
		addInfoMessage("parecer.msgSucesso");
	}
	
	public void prepareEditarParecer() {
		editandoParecer = true;
	}
	
	public boolean isPodeEditarParecer() {
		return atividadeAlunoSelecionada.getParecer() != null
					&& atividadeAlunoSelecionada.getParecer().getProfessor().equals(usuario.getProfessor());
	}

	public void editarParecer() {
		parecerAtividadeAlunoBO.atualizar(atividadeAlunoSelecionada.getParecer());
		addInfoMessage("parecer.msgSucesso");
	}
	
	public void excluirParecer() {
		parecerAtividadeAlunoBO.excluir(atividadeAlunoSelecionada.getParecer());
		atividadeAlunoSelecionada.setParecer(null);
		limparTabelaAtividades();
		addInfoMessage("padrao.exclusao");		
	}
	
	public void cancelEditarParecer() {
		editandoParecer = false;
	}
	
	public ListaDashboard atividadesPorNatureza(NaturezaEnum natureza) {
		if (mapaAtividades.get(natureza) == null) {
			mapaAtividades.put(natureza, atividadeAlunoBO.listarAtividadesPorNatureza(aluno, natureza));
			somaTotalHoras += mapaAtividades.get(natureza).getSomaTotalHoras();
		}
		return mapaAtividades.get(natureza);
	}

	public Long getTotalCreditosCorrespondentes() {
		if (listaNaturezas != null && !listaNaturezas.isEmpty()) {
			return new Double(Math.floor(((double) somaTotalHoras) / HORAS_CREDITO)).longValue();
		} else {
			return 0l;
		}
	}
	
	public void solicitarAvaliacao() {
		int horasExigidas = Integer.parseInt(getResourcesProvider().getValue("horasExigidasCurso"));
		
		if (somaTotalHoras < horasExigidas) {
			RequestContext.getCurrentInstance().addCallbackParam("mensagem", msgUtil.getMessage("dashboard.msgHorasExigidas"));
		} else {
			usuarioBO.avisarProfessoresAvaliacao(aluno);
			RequestContext.getCurrentInstance().addCallbackParam("mensagem", msgUtil.getMessage("dashboard.msgSolicitacaoAvaliacao"));
		}
	}

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
			mapaParametros.put("ALUNO", aluno.getNome());
			mapaParametros.put("MATRICULA", aluno.getMatricula());			
			mapaParametros.put("PERIODO_INGRESSO", aluno.getPeriodo().getNome());			
			mapaParametros.put("FORMA_INGRESSO", "Não sei");
			mapaParametros.put("TOTAL_HORAS", somaTotalHoras);
			mapaParametros.put("TOTAL_CREDITOS", getTotalCreditosCorrespondentes());
			
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listaRelatorio, true);
			String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/relatorios/quadro_atividades.jasper");
			
			jasperPrint = JasperFillManager.fillReport(reportPath, mapaParametros, beanCollectionDataSource);

			JasperExportManager.exportReportToPdfStream(jasperPrint, outputBytes);
			relatorio = new ByteArrayInputStream(outputBytes.toByteArray());
		} catch(Exception ex) {
			ex.printStackTrace();
			addErrorMessageValue(ex.getMessage());
		} finally {
			outputBytes.close();
		}
		
		return new DefaultStreamedContent(relatorio, "application/pdf", "relatorio_atividades.pdf");
	}
	
	// ########		Getters and Setters		###########

	public Aluno getAluno() {
		return aluno;
	}

	public List<AtividadeAluno> getAtividadesAluno() {
		return atividadesAluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
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
