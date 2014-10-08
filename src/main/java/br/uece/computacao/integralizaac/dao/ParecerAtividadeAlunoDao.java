package br.uece.computacao.integralizaac.dao;

import br.uece.computacao.integralizaac.entity.ParecerAtividadeAluno;

/**
 * @author Jocélio Otávio
 *
 * Classe responsável pelo acesso aos dados dos
 * pareceres dados pelos Coordenadores.
 * 
 */
public class ParecerAtividadeAlunoDao extends
		AbstractDao<ParecerAtividadeAluno> {

	private static final long serialVersionUID = 1L;
	
	/* Método que insere o objeto do tipo ParecerAtividadeAluno
	 * e atualiza esse objeto logo após a sua inserção.
	 */
	@Override
	public void incluir(ParecerAtividadeAluno parecer) {
		super.incluir(parecer);
		getEntityManager().refresh(parecer);
	}

}
