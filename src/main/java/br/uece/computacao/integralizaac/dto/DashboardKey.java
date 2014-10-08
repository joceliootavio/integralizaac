package br.uece.computacao.integralizaac.dto;


/**
 * @author Jocélio Otávio
 *
 * Classe utilizada como chave no mapa de atividades do aluno
 * de uma determinada atividade complementar e um determinado
 * período.
 *  
 */
public class DashboardKey {
	private Long idAtividadeComplementar;
	private String periodo;
	
	public DashboardKey(Long codAtividade, String periodo) {
		this.idAtividadeComplementar = codAtividade;
		this.periodo = periodo;
	}
	
	public Long getIdAtividadeComplementar() {
		return idAtividadeComplementar;
	}

	public String getPeriodo() {
		return periodo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((idAtividadeComplementar == null) ? 0
						: idAtividadeComplementar.hashCode());
		result = prime * result + ((periodo == null) ? 0 : periodo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DashboardKey other = (DashboardKey) obj;
		if (idAtividadeComplementar == null) {
			if (other.idAtividadeComplementar != null)
				return false;
		} else if (!idAtividadeComplementar
				.equals(other.idAtividadeComplementar))
			return false;
		if (periodo == null) {
			if (other.periodo != null)
				return false;
		} else if (!periodo.equals(other.periodo))
			return false;
		return true;
	}

}
