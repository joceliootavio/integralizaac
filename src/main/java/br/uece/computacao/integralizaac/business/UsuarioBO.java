package br.uece.computacao.integralizaac.business;

import java.util.List;

import javax.persistence.NoResultException;

import org.apache.commons.codec.digest.DigestUtils;

import br.uece.computacao.integralizaac.dao.UsuarioDao;
import br.uece.computacao.integralizaac.dto.EmailDto;
import br.uece.computacao.integralizaac.entity.Aluno;
import br.uece.computacao.integralizaac.entity.Professor;
import br.uece.computacao.integralizaac.entity.Usuario;
import br.uece.computacao.integralizaac.enums.PerfilEnum;
import br.uece.computacao.integralizaac.exceptions.BusinessException;
import br.uece.computacao.integralizaac.services.EmailService;
import br.uece.computacao.integralizaac.utils.GeradorSenha;

public class UsuarioBO extends Business<Usuario> {

	private UsuarioDao usuarioDao;
	
	private EmailService emailService;
	
	public UsuarioBO(UsuarioDao dao, EmailService emailService) {
		super(dao);
		this.usuarioDao = dao;
		this.emailService = emailService;
	}
	
	public Usuario efetuarLogin(String login, String senha) {
		try {
			return usuarioDao.buscarUsuario(login, criptografarSenha(senha));
		} catch (NoResultException e) {
			throw new BusinessException("Usuário ou senha inválido.");
		}	
	}
	
	public void cadastrarAluno(Aluno aluno) {
		Usuario usuario = new Usuario(PerfilEnum.Aluno);
		
		usuario.setLogin(aluno.getMatricula());
		
		String senhaGerada = GeradorSenha.getSenhaRandomica();
		usuario.setSenha(criptografarSenha(senhaGerada));
		
		usuario.setAluno(aluno);
		
		validarAluno(usuario);
		
		usuarioDao.incluir(usuario);
		
		enviarEmailNovoAluno(usuario, senhaGerada);
	}
	
	private void enviarEmailNovoAluno(Usuario usuario, String senhaUsuario) {
		EmailDto email = new EmailDto();
		email.setAssunto("Novo usuário");
		email.setDestinatarios(usuario.getAluno().getEmail());
		
		StringBuilder corpo = new StringBuilder()
			.append("Foi criado um novo usuário no IntegralizaAC com Matrícula <b>")
			.append(usuario.getAluno().getMatricula())
			.append("</b> e senha <b>")
			.append(senhaUsuario).append("</b>");
		
		email.setCorpo(corpo.toString());
		
		emailService.enviarEmail(email);
	}
	
	private void enviarEmailNovoProfessor(Usuario usuario, String senhaUsuario) {
		EmailDto email = new EmailDto();
		email.setAssunto("Novo usuário");
		email.setDestinatarios(usuario.getProfessor().getEmail());
		
		StringBuilder corpo = new StringBuilder()
			.append("Foi criado um novo usuário no IntegralizaAC com matrícula <b>")
			.append(usuario.getProfessor().getMatricula()).append("</b>.<br/> <br/>")
			.append("Para acessar o sistema utilize o e-mail como login, e a senha <b>")
			.append(senhaUsuario).append("</b>");
		
		email.setCorpo(corpo.toString());
		
		emailService.enviarEmail(email);
	}	
	
	public void cadastrarProfessor(Professor professor) {
		Usuario usuario = new Usuario(PerfilEnum.Professor);
		
		usuario.setLogin(professor.getEmail());
		
		String senhaGerada = GeradorSenha.getSenhaRandomica();
		usuario.setSenha(criptografarSenha(senhaGerada));
		
		usuario.setProfessor(professor);
		
		validarProfessor(usuario);
		
		usuarioDao.incluir(usuario);
		
		enviarEmailNovoProfessor(usuario, senhaGerada);
	}
	
	@Override
	public void atualizar(Usuario usuario) {
		if (usuario.getPerfil() == PerfilEnum.Professor && usuario.getProfessor() != null) {
			validarProfessor(usuario);
		} else if (usuario.getPerfil() == PerfilEnum.Aluno && usuario.getAluno() != null) {
			validarAluno(usuario);
		}
		
		super.atualizar(usuario);
	}
	
	private void validarAluno(Usuario usuario) {
		try {
			Aluno alunoPersistido = usuarioDao.buscarAlunoComMatricula(usuario.getAluno().getMatricula());
			
			if (alunoPersistido != null  
					&& (usuario.getAluno().getId() == null || usuario.getAluno().getId() != alunoPersistido.getId())) {
				throw new BusinessException("Existe aluno cadastro com a matrícula informada.");
			}
		} catch (NoResultException e) {
			// Continua
		}
	}
	
	private void validarProfessor(Usuario usuario) {
		try {
			if (usuario.getProfessor() == null) {
				throw new IllegalArgumentException("Objeto professor está nulo.");
			}
			
			Professor professorPersistido = usuarioDao.buscarProfessorComEmail(usuario.getProfessor().getEmail());
			if (professorPersistido != null  
					&& (usuario.getProfessor().getId() == null || usuario.getProfessor().getId() != professorPersistido.getId())) {
				throw new BusinessException("Existe professor cadastrado com o E-mail informado.");
			}
		} catch (NoResultException e) {
			// Continua
		}
	}
	
	public void avisarProfessoresAvaliacao(Aluno aluno) {
		List<Usuario> professores = usuarioDao.listarTodosProfessores();
		
		for (Usuario usuario : professores) {
			enviarEmailAvaliacao(aluno, usuario.getProfessor());
		}
	}

	private void enviarEmailAvaliacao(Aluno aluno, Professor professor) {
		EmailDto email = new EmailDto();
		email.setAssunto("Solicitação de Avaliação");
		email.setDestinatarios(professor.getEmail());
		
		StringBuilder corpo = new StringBuilder()
			.append("O aluno <b>").append(aluno.getNome())
			.append("</b> com Matrícula <b>").append(aluno.getMatricula())
			.append("</b> solicitou a avaliação de suas atividades no IntegralizaAC.");
		
		email.setCorpo(corpo.toString());
		
		emailService.enviarEmail(email);
	}
	
	public List<Usuario> listarTodosProfessores() {
		return usuarioDao.listarTodosProfessores();
	}
	
	public List<Usuario> listarTodosAlunos() {
		return usuarioDao.listarTodosAlunos();
	}
	
	public List<Aluno> buscarAlunosComMatriculaOuNome(String query) {
		return usuarioDao.buscarAlunoComMatriculaOuNome(query);
	}
	
	public Aluno buscaAlunoPorId(Long idAluno) {
		return usuarioDao.buscarAlunoPorId(idAluno);
	}

	public void resetarSenhaAluno(Usuario usuario) {
		String senhaGerada = GeradorSenha.getSenhaRandomica();
		usuario.setSenha(criptografarSenha(senhaGerada));
		
		usuarioDao.atualizar(usuario);
		
		EmailDto email = new EmailDto();
		email.setAssunto("Nova senha usuário");
		email.setDestinatarios(usuario.getAluno().getEmail());
		
		StringBuilder corpo = new StringBuilder()
			.append("Sua nova senha para acesso ao IntegralizaAC é <b>")
			.append(senhaGerada).append("</b>");
		
		email.setCorpo(corpo.toString());
		
		emailService.enviarEmail(email);
	}
	
	private String criptografarSenha(String senha) {
		return DigestUtils.md5Hex(senha);
	}
	
	public static void main(String[] args) {
		System.out.println(DigestUtils.md5Hex("qcwOOy"));
		System.out.println(DigestUtils.md5Hex("sRhWhk"));		
		
	}
}
