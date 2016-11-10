package br.uece.computacao.integralizaac.business;

import java.util.List;

import javax.persistence.NoResultException;

import org.apache.commons.codec.digest.DigestUtils;

import br.uece.computacao.integralizaac.dao.UsuarioDao;
import br.uece.computacao.integralizaac.dto.EmailDto;
import br.uece.computacao.integralizaac.entity.Aluno;
import br.uece.computacao.integralizaac.entity.Coordenador;
import br.uece.computacao.integralizaac.entity.Usuario;
import br.uece.computacao.integralizaac.enums.PerfilEnum;
import br.uece.computacao.integralizaac.exceptions.BusinessException;
import br.uece.computacao.integralizaac.services.EmailService;
import br.uece.computacao.integralizaac.utils.GeradorSenha;
import br.uece.computacao.integralizaac.utils.ResourcesProvider;

/**
 * @author Jocélio Otávio
 * Classe responsável pelas regras de negócio da entidade
 * @see Usuario.
 */
public class UsuarioBO extends Business<Usuario> {

	/**
	 * Objeto da classe de persistencia da entidade Usuario
	 */
	private UsuarioDao usuarioDao;
	
	/**
	 * Objeto da classe responável por enviar emails.
	 */
	private EmailService emailService;

	private ResourcesProvider resourcesProvider;
	
	public UsuarioBO(UsuarioDao dao, EmailService emailService) {
		super(dao);
		this.usuarioDao = dao;
		this.emailService = emailService;
		this.resourcesProvider = new ResourcesProvider();
	}
	
	/**
	 * Método que retorna o usuário que tenha o mesmo login e
	 * senha passado como parâmetro.
	 * 
	 * @param login Login no sistema.
	 * @param senha Senha
	 * @return Usuario quando autenticar com sucesso, ou nulo
	 */
	public Usuario efetuarLogin(String login, String senha) {
		try {
			return usuarioDao.buscarUsuario(login, criptografarSenha(senha));
		} catch (NoResultException e) {
			throw new BusinessException("Usuário ou senha inválido.");
		}	
	}
	
	/**
	 * Método que cadastra um novo aluno, gerando a senha
	 * automática e enviando email para o aluno com 
	 * a nova senha gerada.
	 * 
	 * @param aluno
	 */
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
	
	/**
	 * Método que monta e envia o email de cadastro de um novo aluno.
	 *  
	 * @param usuario Usuario com os dados do aluno.
	 * @param senhaUsuario Senha gerada para o usuário
	 */
	private void enviarEmailNovoAluno(Usuario usuario, String senhaUsuario) {
		EmailDto email = new EmailDto();
		email.setAssunto("Novo usuário");
		email.setDestinatarios(usuario.getAluno().getEmail());
		
		StringBuilder corpo = new StringBuilder()
			.append("Foi criado um novo usuário no IntegralizaAC com Matrícula <b>")
			.append(usuario.getAluno().getMatricula())
			.append("</b> e senha <b>")
			.append(senhaUsuario).append("</b>. ")
			.append(" A url de acesso ao sistema é <a href=\"").append(resourcesProvider.getValue("pathApp")).append("\">").append(resourcesProvider.getValue("pathApp")).append("</a>.");
		
		email.setCorpo(corpo.toString());
		
		emailService.enviarEmail(email);
	}
	
	/**
	 * Método que monta e envia o email de cadastro de um novo coordenador.
	 *  
	 * @param usuario Usuario com os dados do coordenador.
	 * @param senhaUsuario Senha gerada para o usuário
	 */
	private void enviarEmailNovoCoordenador(Usuario usuario, String senhaUsuario) {
		EmailDto email = new EmailDto();
		email.setAssunto("Novo usuário");
		email.setDestinatarios(usuario.getCoordenador().getEmail());
		
		StringBuilder corpo = new StringBuilder()
			.append("Foi criado um novo usuário no IntegralizaAC com matrícula <b>")
			.append(usuario.getCoordenador().getMatricula()).append("</b>.<br/> <br/>")
			.append(" Acesse o sistema pelo endereço <a href=\"").append(resourcesProvider.getValue("pathApp")).append("\">").append(resourcesProvider.getValue("pathApp")).append("</a>")
			.append(" utilizando o e-mail como login, e a senha <b>")
			.append(senhaUsuario).append("</b>.");
		
		email.setCorpo(corpo.toString());
		
		emailService.enviarEmail(email);
	}	
	
	/**
	 * Método que cadastra um novo coordenador, gerando a senha
	 * automática e enviando email para o coordenador com 
	 * a nova senha gerada.
	 * 
	 * @param coordenador 
	 */
	public void cadastrarCoordenador(Coordenador coordenador) {
		Usuario usuario = new Usuario(PerfilEnum.Coordenador);
		
		usuario.setLogin(coordenador.getEmail());
		
		String senhaGerada = GeradorSenha.getSenhaRandomica();
		usuario.setSenha(criptografarSenha(senhaGerada));
		
		usuario.setCoordenador(coordenador);
		
		validarCoordenador(usuario);
		
		usuarioDao.incluir(usuario);
		
		enviarEmailNovoCoordenador(usuario, senhaGerada);
	}
	
	/* (non-Javadoc)
	 * @see br.uece.computacao.integralizaac.business.Business#atualizar(br.uece.computacao.integralizaac.entity.BaseEntity)
	 * Método de alteração do usuário que faz a validação do
	 * aluno ou coordenador, dependendo do perfil, antes de alterar o usuário.
	 * 
	 */
	@Override
	public void atualizar(Usuario usuario) {
		if (usuario.getPerfil() == PerfilEnum.Coordenador && usuario.getCoordenador() != null) {
			validarCoordenador(usuario);
		} else if (usuario.getPerfil() == PerfilEnum.Aluno && usuario.getAluno() != null) {
			validarAluno(usuario);
		}
		
		super.atualizar(usuario);
	}
	
	/**
	 * Valida o aluno verificando se existe outro aluno com a mesma
	 * matrícula.
	 * 
	 * @param usuario Usuario com os dados do aluno
	 */
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
	
	/**
	 * Valida o coordenador verificando se existe outro coordenador com a mesma
	 * matrícula.
	 * 
	 * @param usuario Usuario com os dados do coordenador
	 */	
	private void validarCoordenador(Usuario usuario) {
		try {
			if (usuario.getCoordenador() == null) {
				throw new IllegalArgumentException("Objeto coordenador está nulo.");
			}
			
			Coordenador coordenadorPersistido = usuarioDao.buscarCoordenadorComEmail(usuario.getCoordenador().getEmail());
			if (coordenadorPersistido != null  
					&& (usuario.getCoordenador().getId() == null || usuario.getCoordenador().getId() != coordenadorPersistido.getId())) {
				throw new BusinessException("Existe coordenador cadastrado com o E-mail informado.");
			}
		} catch (NoResultException e) {
			// Continua
		}
	}
	
	/**
	 * Método que envia email para todos os coordenadores cadastrados
	 * no sistema, avisando da avaliação solicitada por um determinado
	 * aluno.
	 * 
	 * @param aluno Aluno que solicitou a avaliação.
	 */
	public void avisarCoordenadoresAvaliacao(Aluno aluno) {
		List<Usuario> coordenadores = usuarioDao.listarTodosCoordenadores();
		
		for (Usuario usuario : coordenadores) {
			enviarEmailAvaliacao(aluno, usuario.getCoordenador());
		}
	}

	/**
	 * Método que monta e envia email para o coordenador do curso.
	 *  
	 * @param aluno Aluno que solicitou a avaliação
	 * @param coordenador Coordenador que receberá o email.
	 */
	private void enviarEmailAvaliacao(Aluno aluno, Coordenador coordenador) {
		EmailDto email = new EmailDto();
		email.setAssunto("Solicitação de Avaliação");
		email.setDestinatarios(coordenador.getEmail());
		
		StringBuilder corpo = new StringBuilder()
			.append("O aluno <b>").append(aluno.getNome())
			.append("</b> com Matrícula <b>").append(aluno.getMatricula())
			.append("</b> solicitou a avaliação de suas atividades no IntegralizaAC.");
		
		email.setCorpo(corpo.toString());
		
		emailService.enviarEmail(email);
	}
	
	/**
	 * Método que retorna a lista de todos os coordenadores
	 * cadastrados na base de dados.
	 * 
	 * @return Lista com todos os coordenadores
	 */
	public List<Usuario> listarTodosCoordenadores() {
		return usuarioDao.listarTodosCoordenadores();
	}
	
	/**
	 * Método que retorna a lista de todos os alunos
	 * cadastrados na base de dados.
	 * 
	 * @return Lista com todos os alunos
	 */
	public List<Usuario> listarTodosAlunos() {
		return usuarioDao.listarTodosAlunos();
	}
	
	/**
	 * Método que retorna uma lista de alunos contendo na matricula
	 * ou no nome o texto passado como parâmetro.
	 * 
	 * @param query Parte da matrícula ou nome que deve ser pesquisado.
	 * @return Lista de alunos.
	 */
	public List<Aluno> buscarAlunosComMatriculaOuNome(String query) {
		return usuarioDao.buscarAlunoComMatriculaOuNome(query);
	}
	
	/**
	 * Método que retorna o aluno com o mesmo Id passado como
	 * parâmetro.
	 * 
	 * @param idAluno Id do aluno.
	 * @return Aluno com o Id informado, ou nulo.
	 */
	public Aluno buscaAlunoPorId(Long idAluno) {
		return usuarioDao.buscarAlunoPorId(idAluno);
	}

	/**
	 * Método que gera uma nova senha, atualiza o usuário e
	 * envia email para o usuário com a nova senha.
	 * 
	 * @param usuario Usuário que deve ter a senha resetada.
	 */
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
	
	/**
	 * Método que criptografa a senha.
	 * 
	 * @param senha Senha a ser criptografada.
	 * @return Senha criptografada.
	 */
	private String criptografarSenha(String senha) {
		return DigestUtils.md5Hex(senha);
	}

}
