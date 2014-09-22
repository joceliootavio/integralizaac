package br.uece.computacao.integralizaac.business;

import br.uece.computacao.integralizaac.dao.ParecerAtividadeAlunoDao;
import br.uece.computacao.integralizaac.dto.EmailDto;
import br.uece.computacao.integralizaac.entity.AtividadeAluno;
import br.uece.computacao.integralizaac.entity.ParecerAtividadeAluno;
import br.uece.computacao.integralizaac.services.EmailService;

public class ParecerAtividadeAlunoBO extends Business<ParecerAtividadeAluno>{
	
	private EmailService emailService;
	
	public ParecerAtividadeAlunoBO(ParecerAtividadeAlunoDao dao, EmailService emailService) {
		super(dao);
		this.emailService = emailService;
	}
	
	@Override
	public void incluir(ParecerAtividadeAluno parecer) {
		super.incluir(parecer);
		dao.flush();
		
		enviarEmailParecerProfessor(parecer);
	}
	
	@Override
	public void atualizar(ParecerAtividadeAluno parecer) {
		super.atualizar(parecer);
		dao.flush();
		
		enviarEmailParecerProfessor(parecer);
	}
	
	@Override
	public void excluir(ParecerAtividadeAluno parecer) {
		super.excluir(parecer);
		dao.flush();
		
		enviarEmailParecerProfessor(parecer);
	}
	
	private void enviarEmailParecerProfessor(final ParecerAtividadeAluno parecer) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				AtividadeAluno atividadeAluno = parecer.getAtividadeAluno();
				EmailDto email = new EmailDto();
				email.setAssunto("Parecer do Professor");
				email.setDestinatarios(atividadeAluno.getAluno().getEmail());
				
				StringBuilder corpo = new StringBuilder()
				.append("Aluno(a) <b>").append(atividadeAluno.getAluno().getNome()).append("</b>.<br/><br/>")
				.append("As informações de parecer do professor para a atividade <b>").append(atividadeAluno.getDescricao()).append("</b> foram atualizadas.");
				
				email.setCorpo(corpo.toString());
				
				emailService.enviarEmail(email);
			}
		}).start();
	}
}
