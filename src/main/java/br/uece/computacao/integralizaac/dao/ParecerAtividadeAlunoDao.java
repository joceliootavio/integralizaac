package br.uece.computacao.integralizaac.dao;

import br.uece.computacao.integralizaac.entity.ParecerAtividadeAluno;

public class ParecerAtividadeAlunoDao extends
		AbstractDao<ParecerAtividadeAluno> {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void incluir(ParecerAtividadeAluno parecer) {
		super.incluir(parecer);
		getEntityManager().refresh(parecer);
	}

}
