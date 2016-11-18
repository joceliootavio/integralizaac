package br.uece.computacao.integralizaac.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import br.uece.computacao.integralizaac.entity.Aluno;
import br.uece.computacao.integralizaac.entity.Coordenador;
import br.uece.computacao.integralizaac.entity.Usuario;

/**
 * @author Jocélio Otávio
 * 
 * Classe responsável por acesso aos dados das entidades
 * Usuários.
 */
public class UsuarioDao extends
		AbstractDao<Usuario> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Método que retorna o usuário que tenho o mesmo login e senha
	 * do que o passado como parâmetro, quando existir.
	 *  
	 * @param login Login do usuário.
	 * @param senha Senha do usuário.
	 * @return
	 */
	public Usuario buscarUsuario(String login, String senha) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select u from Usuario as u ");
		hql.append("where u.login = :login ");
		hql.append("and u.senha = :senha ");		
		
		TypedQuery<Usuario> query = getEntityManager()
				.createQuery(hql.toString(), Usuario.class);
		
		query.setParameter("login", login.trim());
		query.setParameter("senha", senha);
		
		return query.getSingleResult();
	}
	
	/**
	 * Método que recupera o Coordenador que tenha email igual
	 * ao email passado como parâmetro.
	 * 
	 * @param email Email do Coordenador
	 * 
	 * @return Objeto do tipo Coordenador
	 */
	public Coordenador buscarCoordenadorComEmail(String email) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select p from Coordenador as p ");
		hql.append("where p.email = :email ");
		
		TypedQuery<Coordenador> query = getEntityManager()
				.createQuery(hql.toString(), Coordenador.class);
		
		query.setParameter("email", email);
		
		return query.getSingleResult();
	}
	
	/**
	 * Método que lista todos os coordenadores cadastrados.
	 * 
	 * @return Lista de usuários coordenadores.
	 */
	public List<Usuario> listarTodosCoordenadores() {
		TypedQuery<Usuario> query = getEntityManager()
				.createNamedQuery(Usuario.FIND_ALL_COORDENADOR, Usuario.class);
		
		return query.getResultList();
	}

	/**
	 * Método que lista de todos os alunos cadastrados.
	 * 
	 * @return Lista de usuários alunos.
	 */
	public List<Usuario> listarTodosAlunos() {
		TypedQuery<Usuario> query = getEntityManager()
				.createNamedQuery(Usuario.FIND_ALL_ALUNO, Usuario.class);
		
		return query.getResultList();
	}
	
	/**
	 * Retorna aluno que tenha matrícula igual a passada como
	 * parâmetro.
	 * 
	 * @param matricula Matrícula do aluno
	 * 
	 * @return Aluno com matrícula informada.
	 */
	public Aluno buscarAlunoComMatricula(String matricula) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select a from Aluno as a ");
		hql.append("where a.matricula = :matricula ");
		
		TypedQuery<Aluno> query = getEntityManager()
				.createQuery(hql.toString(), Aluno.class);
		
		query.setParameter("matricula", matricula);
		
		return query.getSingleResult();
	}
	
	/**
	 * Retorna usuario que tenha matrícula igual a passada como
	 * parâmetro.
	 * 
	 * @param login Matrícula do aluno
	 * 
	 * @return Aluno com matrícula informada.
	 */
	public Usuario buscarUsuario(String login) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select u from Usuario as u ");
		hql.append("where u.login = :login ");
		
		TypedQuery<Usuario> query = getEntityManager()
				.createQuery(hql.toString(), Usuario.class);
		
		query.setParameter("login", login);
		
		return query.getSingleResult();
	}	
	
	/**
	 * Recuperar aluno com o Id passado como parâmetro.
	 * 
	 * @param idAluno Id do aluno.
	 * 
	 * @return Objeto do tipo @see Aluno.
	 */
	public Aluno buscarAlunoPorId(Long idAluno) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select a from Aluno as a ");
		hql.append("where a.id = :idAluno ");
		
		TypedQuery<Aluno> query = getEntityManager()
				.createQuery(hql.toString(), Aluno.class);
		
		query.setParameter("idAluno", idAluno);
		
		return query.getSingleResult();
	}	

	/**
	 * Recupera os alunos que contenha no nome ou na matrícula
	 * o valor passado como parâmetro.
	 * 
	 * @param query Parte do nome ou matrícula do aluno.
	 * 
	 * @return Lista de alunos filtrada.
	 */
	public List<Aluno> buscarAlunoComMatriculaOuNome(String query) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select a from Aluno as a ");
		hql.append("where UPPER(a.matricula) like UPPER(:matricula) ");
		hql.append("or UPPER(a.nome) like UPPER(:nome) ");		
		
		TypedQuery<Aluno> typedQuery = getEntityManager()
				.createQuery(hql.toString(), Aluno.class);
		
		String queryCoringa = query + "%";
		typedQuery.setParameter("matricula", queryCoringa);
		typedQuery.setParameter("nome", queryCoringa);		
		
		return typedQuery.getResultList();
	}
	
}
