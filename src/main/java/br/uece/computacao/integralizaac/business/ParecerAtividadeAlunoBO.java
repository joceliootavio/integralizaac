package br.uece.computacao.integralizaac.business;

import br.uece.computacao.integralizaac.dao.ParecerAtividadeAlunoDao;
import br.uece.computacao.integralizaac.dto.EmailDto;
import br.uece.computacao.integralizaac.entity.AtividadeAluno;
import br.uece.computacao.integralizaac.entity.ParecerAtividadeAluno;
import br.uece.computacao.integralizaac.services.EmailService;

/**
 * @author Jocélio Otávio
 *
 * Classe responsável pelas regras de negócio da entidade
 * @see ParecerAtividadeAluno.
 */
public class ParecerAtividadeAlunoBO extends Business<ParecerAtividadeAluno>{
	
	/**
	 * Objeto da classe responável por enviar emails.
	 */
	private EmailService emailService;
	
	public ParecerAtividadeAlunoBO(ParecerAtividadeAlunoDao dao, EmailService emailService) {
		super(dao);
		this.emailService = emailService;
	}
	
	/* (non-Javadoc)
	 * @see br.uece.computacao.integralizaac.business.Business#incluir(br.uece.computacao.integralizaac.entity.BaseEntity)
	 * 
	 * Método de inclusão que envia email para o aluno após inserir
	 * parecer do coordenador.
	 * 
	 */
	@Override
	public void incluir(ParecerAtividadeAluno parecer) {
		super.incluir(parecer);
		dao.flush();
		
		enviarEmailParecerCoordenador(parecer);
	}
	
	/* (non-Javadoc)
	 * @see br.uece.computacao.integralizaac.business.Business#atualizar(br.uece.computacao.integralizaac.entity.BaseEntity)
	 * 
	 * Método de alteração que envia email para o aluno
	 * após alterar o parecer do coordenador.
	 * 
	 */
	@Override
	public void atualizar(ParecerAtividadeAluno parecer) {
		super.atualizar(parecer);
		dao.flush();
		
		enviarEmailParecerCoordenador(parecer);
	}
	
	@Override
	public void excluir(ParecerAtividadeAluno parecer) {
		super.excluir(parecer);
		dao.flush();
		
		enviarEmailParecerCoordenador(parecer);
	}
	
	/**
	 * Método que monta o email de envio para o aluno com os 
	 * dados do parecer dado pelo coordenador. O método é assíncrono
	 * para não impedir que o coordenador dê o parecer da atividade.
	 * 
	 * @param parecer Parecer do coordenador.
	 */
	private void enviarEmailParecerCoordenador(final ParecerAtividadeAluno parecer) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				AtividadeAluno atividadeAluno = parecer.getAtividadeAluno();
				EmailDto email = new EmailDto();
				email.setAssunto("Parecer do Coordenador");
				email.setDestinatarios(atividadeAluno.getAluno().getEmail());
				
				StringBuilder corpo = new StringBuilder()
				.append("Aluno(a) <b>").append(atividadeAluno.getAluno().getNome()).append("</b>.<br/><br/>")
				.append("As informações de parecer do coordenador para a atividade <b>").append(atividadeAluno.getDescricao()).append("</b> foram atualizadas.");
				
				email.setCorpo(corpo.toString());
				
				emailService.enviarEmail(email);
			}
		}).start();
	}
}
