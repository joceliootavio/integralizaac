package br.uece.computacao.integralizaac.reports;

import java.util.ArrayList;
import java.util.List;

import br.uece.computacao.integralizaac.dto.DashboardDTO;
import br.uece.computacao.integralizaac.dto.ListaDashboard;
import br.uece.computacao.integralizaac.enums.NaturezaEnum;

/**
 * @author Jocélio Otávio
 *
 * Classe que contém as informações utilizadas na montagem do
 * relatório de Mapa de registro de atividades complementares.
 * 
 */
public class RelatorioVO {
	/**
	 * A natureza das atividades.
	 */
	private NaturezaEnum natureza;
	
	/**
	 * A lista de DTOs que serão exibidos no relatório. 
	 */
	private List<DashboardDTO> listaDtos = new ArrayList<DashboardDTO>();
	
	/**
	 * Total de linhas que serão exibidas no relatório. 
	 */
	private int countDto;
	
	/**
	 * Soma da carga horária das atividades dessa natureza. 
	 */
	private int somaHorasNatureza;
	
	/**
	 * Construtor que recebe o contador de linhas como parâmetro
	 * para que o mesmo possa ser compartilhado por todas as
	 * naturezas de atividades.
	 * 
	 * @param countDto Contador de linhas.
	 */
	public RelatorioVO(int countDto) {
		super();
		this.countDto = countDto;
	}
	public NaturezaEnum getNatureza() {
		return natureza;
	}
	public void setNatureza(NaturezaEnum natureza) {
		this.natureza = natureza;
	}
	public List<DashboardDTO> getListaDtos() {
		return listaDtos;
	}
	public int getSomaHorasNatureza() {
		return somaHorasNatureza;
	}
	public int getCountDto() {
		return countDto;
	}
	
	/**
	 * Método que adiciona todos os DTOs da lista de DTO
	 * passada como parâmetro, com exceção das atividades
	 * reprovadas pelo Coordenador.
	 * 
	 * @param listaDashboard Lista de DTOs
	 */
	public void addDtos(ListaDashboard listaDashboard) {
		for (DashboardDTO dto : listaDashboard.getListaDtos()) {
			if (dto.getStatus() == null || dto.getStatus()) {
				dto.setOrdem(++countDto);
				somaHorasNatureza += dto.getCargaHorariaAproveitada();
				listaDtos.add(dto);
			}
		}
	}
}
