package br.uece.computacao.integralizaac.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import br.uece.computacao.integralizaac.enums.PerfilEnum;

@Entity
@NamedQueries({@NamedQuery(name=Usuario.FIND_ALL_PROFESSOR, query="select u from Usuario as u where u.perfil = br.uece.computacao.integralizaac.enums.PerfilEnum.Professor"),
				@NamedQuery(name=Usuario.FIND_ALL_ALUNO, query="select u from Usuario as u where u.perfil = br.uece.computacao.integralizaac.enums.PerfilEnum.Aluno")})
public class Usuario extends BaseEntity{
	
	public static final String FIND_ALL_PROFESSOR = "findAllProfessor";
	public static final String FIND_ALL_ALUNO = "findAllAluno";	

	@Column(nullable=false, length=30)
	private String login;
	
	@Column(nullable=false, length=32)
	private String senha;
	
	@Column
	private boolean ativo;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false, updatable=false)
	private PerfilEnum perfil;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Aluno aluno;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Professor professor;
	
	public Usuario() {
		this(null);
	}

	public Usuario(PerfilEnum perfil) {
		this.perfil = perfil;
		
		if (perfil == PerfilEnum.Professor){
			professor = new Professor();
		} else if (perfil == PerfilEnum.Aluno){
			aluno = new Aluno();
		}
		
		// TODO Após implementacao da ativacao do aluno essa linha deve ser condicionada
		ativo = true;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public PerfilEnum getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilEnum perfil) {
		this.perfil = perfil;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

}
