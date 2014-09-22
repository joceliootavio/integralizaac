package br.uece.computacao.integralizaac.reports;

import java.util.ArrayList;
import java.util.List;

import br.uece.computacao.integralizaac.dto.DashboardDTO;
import br.uece.computacao.integralizaac.dto.ListaDashboard;
import br.uece.computacao.integralizaac.enums.NaturezaEnum;

public class RelatorioVO {
	private NaturezaEnum natureza;
	private List<DashboardDTO> listaDtos = new ArrayList<DashboardDTO>();
	private int countDto;
	private int somaHorasNatureza;
	
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
