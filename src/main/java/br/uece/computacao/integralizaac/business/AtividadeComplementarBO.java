package br.uece.computacao.integralizaac.business;

import java.util.List;

import br.uece.computacao.integralizaac.dao.AtividadeComplementarDao;
import br.uece.computacao.integralizaac.entity.AtividadeComplementar;
import br.uece.computacao.integralizaac.entity.Curso;
import br.uece.computacao.integralizaac.enums.NaturezaEnum;
import br.uece.computacao.integralizaac.exceptions.BusinessException;
import br.uece.computacao.integralizaac.utils.MsgUtil;

/**
 * @author Jocélio Otávio
 *
 * Classe responsável pelas regras de negócio da entidade
 * @see AtividadeComplementar.
 */
public class AtividadeComplementarBO extends Business<AtividadeComplementar> {
	
	/**
	 * Objeto da classe de persistencia da entidade @see AtividadeComplementar
	 */
	private AtividadeComplementarDao ativComplementarDao;
	
	/**
	 * Objeto da classe de mensagens do sistema. 
	 */
	private MsgUtil msgUtil;
	
	public AtividadeComplementarBO(AtividadeComplementarDao dao) {
		super(dao);
		this.ativComplementarDao = dao;
		this.msgUtil = new MsgUtil();		
	}
	
	/* (non-Javadoc)
	 * @see br.uece.computacao.integralizaac.business.Business#incluir(br.uece.computacao.integralizaac.entity.BaseEntity)
	 * 
	 * Método de inclusão faz antes a validação da atividade complementar
	 * a ser incluída.
	 */
	@Override
	public void incluir(AtividadeComplementar atividadeComplementar) {
		validarAtividadeComplementar(atividadeComplementar);
		
		dao.incluir(atividadeComplementar);
	}
	
	/* (non-Javadoc)
	 * @see br.uece.computacao.integralizaac.business.Business#atualizar(br.uece.computacao.integralizaac.entity.BaseEntity)
	 * 
	 * Método de alteração faz antes a validação da atividade complementar
	 * a ser alterada. 
	 */
	@Override
	public void atualizar(AtividadeComplementar atividadeComplementar) {
		validarAtividadeComplementar(atividadeComplementar);
		
		dao.atualizar(atividadeComplementar);
	}	
	
	/**
	 * Método que faz a validação da atividade complementar, verificando se
	 * já existe uma outra atividade com o mesmo nome.
	 * 
	 * @param atividadeComplementar Atividade complementar
	 */
	private void validarAtividadeComplementar(AtividadeComplementar atividadeComplementar) {
		List<AtividadeComplementar> atividades = ativComplementarDao.buscarComDescricao(atividadeComplementar.getNatureza(), 
																		atividadeComplementar.getDescricao(), 
																		true);
		
		if (atividades != null) {
			
			if ((!atividadeComplementar.isPesistido() && atividades.size() == 1) 
					|| (atividadeComplementar.isPesistido() && !atividades.isEmpty() && !atividadeComplementar.equals(atividades.get(0)))) {
				throw new BusinessException(msgUtil.getMessage("atividade_complementar.atividadeJaCadastrada"));
			}
		}
	}
	
	/**
	 * Método que retorna a atividade complementar que tenha Id igual
	 * ao código passado como parâmetro.
	 * 
	 * @param codAtividade Código da atividade
	 * @return Atividade complementar
	 */
	public AtividadeComplementar buscaPorCodAtividade(long codAtividade) {
		return ativComplementarDao.buscaPorId(codAtividade);
	}
	
	/**
	 * Método que retorna uma lista com todas as atividades complementares
	 * cadastradas no banco.
	 * 
	 * @return Lista de atividades complementares.
	 */
	public List<AtividadeComplementar> listarTodasAtividades() {
		return ativComplementarDao.listarTodos();
	}

	/**
	 * Método que retorna a atividade complementar que tenha descrição
	 * igual a descrição passada como parâmetro.
	 * 
	 * @param curso Curso no qual o aluno está matriculado
	 * @param natureza Natureza da atividade 
	 * @param query Todo ou parte do descrição da atividade complementar.
	 * @return
	 */
	public List<AtividadeComplementar> buscarComDescricao(Curso curso, NaturezaEnum natureza, String query) {
		return ativComplementarDao.buscarComDescricao(curso, natureza, query, false);
	}
	
	
	/**
	 * Método que verifica se tem aluno utilizando a atividade passada como
	 * parâmetro.
	 *  
	 * @param atividadeComplementarId ID da atividade.
	 * @return Quantidade de alunos utilizando a atividade
	 */
	public boolean verificaAtividadeUtilizada(Long atividadeComplementarId) {
		if (ativComplementarDao.verificaAtividadeUtilizada(atividadeComplementarId) > 0) {
			return true;
		} else {
			return false;
		}
	}
}
