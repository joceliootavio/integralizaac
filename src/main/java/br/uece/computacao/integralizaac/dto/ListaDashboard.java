package br.uece.computacao.integralizaac.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jocélio Otávio
 *
 * Classe que contém a lista de DTOs que serão exibidos no Dashboard.
 * os atributos do tipo mapaSomaAtividadePeriodo e mapaSomaAtividade
 * são utilizados para calculo da carga horária das atividades do 
 * aluno. 
 * 
 */
public class ListaDashboard {
	
	/**
	 * Mapa que tem como chave a atividade complementar e o período,
	 * a fim de guardar a carga horária total para cada combinação
	 * desses dois valores. 
	 */
	private Map<DashboardKey, Integer> mapaSomaAtividadePeriodo;
	
	/**
	 * Mapa que tem como chave o id da atividade complementar a fim
	 * de somar a carga horária total de uma determinada 
	 * atividade complementar exercida pelo aluno. 
	 */
	private Map<Long, Integer> mapaSomaAtividade;
	
	/**
	 * Lista de DTOs que serão exibidos no Dashboard. 
	 */
	private List<DashboardDTO> listaDtos;
	
	public ListaDashboard() {
		this.mapaSomaAtividadePeriodo = new HashMap<DashboardKey, Integer>();
		this.mapaSomaAtividade = new HashMap<Long, Integer>();		
		this.listaDtos = new ArrayList<DashboardDTO>();
	}
	
	public void addDto(DashboardDTO dto) {
		DashboardKey key = new DashboardKey(dto.getIdAtividadeComplementar(), dto.getPeriodo());
		
		Integer somaHorasPeriodo = mapaSomaAtividadePeriodo.get(key) == null ? 0 : mapaSomaAtividadePeriodo.get(key);
		Integer cargaHorariaAproveitada = 0;
		
		if (dto.getMaxHorasPeriodo() != null && (somaHorasPeriodo + dto.getCargaHoraria()) > dto.getMaxHorasPeriodo()) {
			cargaHorariaAproveitada = dto.getMaxHorasPeriodo() - somaHorasPeriodo;
			somaHorasPeriodo = dto.getMaxHorasPeriodo();
		} else {
			cargaHorariaAproveitada = dto.getCargaHoraria();
			somaHorasPeriodo += cargaHorariaAproveitada;
		}
		
		// Não contabiliza horas de atividades reprovadas. 
		if (dto.getStatus() == null || dto.getStatus()) {
			mapaSomaAtividadePeriodo.put(key, somaHorasPeriodo);
		}
		
		Integer somaHorasAtividade = mapaSomaAtividade.get(dto.getIdAtividadeComplementar()) == null ? 0 : mapaSomaAtividade.get(dto.getIdAtividadeComplementar());		
		if ((somaHorasAtividade + cargaHorariaAproveitada) > dto.getMaxHorasCurso()) {
			cargaHorariaAproveitada = dto.getMaxHorasCurso() - somaHorasAtividade;
			somaHorasAtividade = dto.getMaxHorasCurso();
		} else {
			somaHorasAtividade += cargaHorariaAproveitada; 
		}

		// Não contabiliza horas de atividades reprovadas.		
		if (dto.getStatus() == null || dto.getStatus()) {		
			mapaSomaAtividade.put(dto.getIdAtividadeComplementar(), somaHorasAtividade);
		}
		
		dto.setCargaHorariaAproveitada(cargaHorariaAproveitada);
		
		listaDtos.add(dto);
	}
	
	public int size() {
		return listaDtos == null ? 0 : listaDtos.size();
	}	
	
	public boolean isEmpty() {
		return listaDtos == null || listaDtos.isEmpty();
	}
	
	public Integer getSomaTotalHoras() {
		int somaTotal = 0;
		
		for (Integer horasAtividade : mapaSomaAtividade.values()) {
			somaTotal += horasAtividade;
		}
		
		return somaTotal;
	}
	
	public List<DashboardDTO> getListaDtos() {
		return listaDtos;
	}
	
}
