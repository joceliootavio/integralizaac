package br.uece.computacao.integralizaac.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import br.uece.computacao.integralizaac.dto.DashboardDTO;
import br.uece.computacao.integralizaac.dto.HorasAtividadeDTO;
import br.uece.computacao.integralizaac.dto.ListaDashboard;
import br.uece.computacao.integralizaac.entity.Aluno;
import br.uece.computacao.integralizaac.entity.AtividadeAluno;
import br.uece.computacao.integralizaac.enums.NaturezaEnum;

/**
 * @author Jocélio Otávio
 *
 * Classe responsável por acesso aos dados das Atividades
 * do Aluno.
 */
public class AtividadeAlunoDao extends
		AbstractDao<AtividadeAluno> {

	private static final long serialVersionUID = 1L;
	
	/* (non-Javadoc)
	 * @see br.uece.computacao.integralizaac.dao.AbstractDao#incluir(java.lang.Object)
	 */
	@Override
	public void incluir(AtividadeAluno atividadeAluno) {
		super.incluir(atividadeAluno);
	}

	/**
	 * Método que Recuṕera todas as atividades de um determinado aluno e natureza
	 * representada por uma lista de objetos DTO contendo todas as informações
	 * que devem ser exibidas na tela de dashboard do Aluno ou Coordenador.
	 * 
	 * @param aluno Aluno do qual deve-se listar as atividades
	 * @param natureza Natureza das atividades que deve ser retornada.
	 * @return Objeto que representa uma lista de objetos DTO.
	 */
	public ListaDashboard listarAtividadesPorNatureza(Aluno aluno, NaturezaEnum natureza) {
		StringBuilder hql = new StringBuilder();
	
		hql.append("select tb1.id, ");  
		hql.append("tb1.descricao atividade, "); 
		hql.append("tb2.id idAtividadeComplementar, ");
		hql.append("tb2.descricao atividadeComplementar, "); 
		hql.append("tb4.nome instituicao, ");
		hql.append("tb3.nome periodo, ");
		hql.append("tb1.cargaHoraria, ");
		hql.append("tb5.aprovado, ");		
		hql.append("tb2.maximohorasporperiodo maxHorasPeriodo, "); 
		hql.append("tb2.maximohorasporcurso maxHorasCurso, ");
		hql.append("tb1.tipoParticipacao, ");
		hql.append("tb1.nomeEvento ");		
		hql.append("from atividadealuno as tb1 ");
		hql.append("join atividadecomplementar tb2 "); 
		hql.append("on tb1.atividade_id = tb2.id ");
		hql.append("join periodo tb3 ");
		hql.append("on tb1.periodo_id = tb3.id ");
		hql.append("join instituicao tb4 ");
		hql.append("on tb1.instituicao_id = tb4.id ");	  
		hql.append("left join pareceratividadealuno tb5 ");
		hql.append("on tb1.id = tb5.atividadealuno_id ");		
		hql.append("where tb1.aluno_id = :alunoId ");
		hql.append("and tb2.natureza = :natureza ");
		hql.append("order by tb2.id, tb3.nome, tb1.dataHoraCadastro ");
	
		Query query = getEntityManager().createNativeQuery(hql.toString());
		
		query.setParameter("alunoId", aluno.getId());
		query.setParameter("natureza", natureza.ordinal());
		
		ListaDashboard listaDtos = new ListaDashboard();
		DashboardDTO dto = null;
		int i = 0;
		
		for (Object row : query.getResultList()) {
			i = 0;
			Object[] rs = (Object[]) row;
			dto = new DashboardDTO();
			
			dto.setIdAtividade(((BigInteger) rs[i++]).longValue());
			dto.setAtividade((String) rs[i++]);
			dto.setIdAtividadeComplementar(((BigInteger) rs[i++]).longValue());
			dto.setAtividadeComplementar((String) rs[i++]);			
			dto.setInstituicao((String) rs[i++]);			
			dto.setPeriodo((String) rs[i++]);			
			dto.setCargaHoraria((Integer) rs[i++]);
			dto.setStatus(((Boolean) rs[i++]));
			dto.setMaxHorasPeriodo((Integer) rs[i++]);
			dto.setMaxHorasCurso((Integer) rs[i++]);
			dto.setTipoParticipacao((String) rs[i++]);
			dto.setNomeEvento((String) rs[i++]);			
			
			listaDtos.addDto(dto);
		}
		
		return listaDtos;
	}

	/**
	 * Método que retorna o total de horas por período e por curso
	 * de um determinado tipo de atividade e aluno.
	 * 
	 * @param atividadeAluno Atividade do aluno.
	 * @return Objeto com as informações de horas calculadas.
	 */
	public HorasAtividadeDTO horasAtividade(AtividadeAluno atividadeAluno) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select periodo_id, horasPeriodo, sum(horasPeriodo) over() horasCurso ");
		hql.append("from ( select aa.periodo_id, ac.maximoHorasPorCurso, ac.maximoHorasPorPeriodo, ");
		hql.append("LEAST(sum(aa.cargaHoraria), ac.maximoHorasPorPeriodo) horasPeriodo ");
		hql.append("from atividadealuno aa ");
		hql.append("inner join atividadecomplementar ac ");
		hql.append("on aa.atividade_id = ac.id ");
		hql.append("left join pareceratividadealuno paa ");
		hql.append("on aa.id = paa.atividadealuno_id ");		
		hql.append("where aa.aluno_id = :alunoId ");
		hql.append("and aa.atividade_id = :atividadeComplementarId ");
		hql.append("and (paa.atividadealuno_id IS NULL OR paa.aprovado = TRUE)");		
		hql.append("group by aa.periodo_id, ac.maximoHorasPorCurso, ac.maximoHorasPorPeriodo) tb ");		

		Query query = getEntityManager()
				.createNativeQuery(hql.toString());
		
		query.setParameter("alunoId", atividadeAluno.getAluno().getId());
		query.setParameter("atividadeComplementarId", atividadeAluno.getAtividade().getId());
		
		for (Object row : query.getResultList()) {
			Object[] values = (Object[]) row;
			
			BigInteger periodoId = (BigInteger)values[0];
			if (periodoId.intValue() == atividadeAluno.getPeriodo().getId()) {
				BigInteger horasPeriodo = (BigInteger) values[1];
				BigDecimal horasCurso = (BigDecimal) values[2];
				
				return new HorasAtividadeDTO(horasPeriodo != null ? horasPeriodo.intValue(): null, horasCurso.intValue());
			}
		}
		
		return null;
	}	

	/**
	 * Listar as naturezas das quais um determinado aluno tem
	 * atividades cadastradas.
	 * 
	 * @param aluno Aluno.
	 * @return Lista de naturezas.
	 */
	public List<NaturezaEnum> listarNaturezas(Aluno aluno) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select distinct ac.natureza ")
		   .append("from atividadecomplementar as ac ")
		   .append("inner join atividadealuno as aa ")
		   .append("on ac.id = aa.atividade_id ")
		   .append("where aa.aluno_id = :alunoId ")		
		   .append("order by ac.natureza ");		

		Query query = getEntityManager()
				.createNativeQuery(hql.toString());
		
		query.setParameter("alunoId", aluno.getId());
		
		List<NaturezaEnum> result = new ArrayList<NaturezaEnum>();
		
		for (Object row : query.getResultList()) {
			Integer naturezaOrdinal = (Integer) row;
			
			result.add(NaturezaEnum.naturezaComOrdinal(naturezaOrdinal) );
		}
		
		return result;
	}	
	
}
