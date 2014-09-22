package br.uece.computacao.integralizaac.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import br.uece.computacao.integralizaac.entity.Aluno;
import br.uece.computacao.integralizaac.entity.Professor;
import br.uece.computacao.integralizaac.entity.Usuario;

public class UsuarioDao extends
		AbstractDao<Usuario> {

	private static final long serialVersionUID = 1L;
	
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
	
	public Professor buscarProfessorComEmail(String email) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select p from Professor as p ");
		hql.append("where p.email = :email ");
		
		TypedQuery<Professor> query = getEntityManager()
				.createQuery(hql.toString(), Professor.class);
		
		query.setParameter("email", email);
		
		return query.getSingleResult();
	}
	
	public List<Usuario> listarTodosProfessores() {
		TypedQuery<Usuario> query = getEntityManager()
				.createNamedQuery(Usuario.FIND_ALL_PROFESSOR, Usuario.class);
		
		return query.getResultList();
	}

	public List<Usuario> listarTodosAlunos() {
		TypedQuery<Usuario> query = getEntityManager()
				.createNamedQuery(Usuario.FIND_ALL_ALUNO, Usuario.class);
		
		return query.getResultList();
	}
	
	public Aluno buscarAlunoComMatricula(String matricula) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select a from Aluno as a ");
		hql.append("where a.matricula = :matricula ");
		
		TypedQuery<Aluno> query = getEntityManager()
				.createQuery(hql.toString(), Aluno.class);
		
		query.setParameter("matricula", matricula);
		
		return query.getSingleResult();
	}
	
	public Aluno buscarAlunoPorId(Long idAluno) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select a from Aluno as a ");
		hql.append("where a.id = :idAluno ");
		
		TypedQuery<Aluno> query = getEntityManager()
				.createQuery(hql.toString(), Aluno.class);
		
		query.setParameter("idAluno", idAluno);
		
		return query.getSingleResult();
	}	

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
