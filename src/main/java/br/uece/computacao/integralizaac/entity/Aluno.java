package br.uece.computacao.integralizaac.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import br.uece.computacao.integralizaac.enums.FormaIngressoEnum;

/**
 * @author Jocélio Otávio
 *
 *	Classe que representa a entidade Aluno.
 */
@Entity
public class Aluno extends BaseEntity {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 8217078288149747337L;
	
	/**
	 * Campo texto obrigatório de 8 caracteres que guarda a 
	 * matrícula do Aluno. Esse campo não aceita valores 
	 * duplicados, uma vez que não pode haver dois alunos 
	 * com a mesma matrícula.
	 */
	@Column(nullable=false, unique=true, length=8)
	private String matricula;
	
	/**
	 * Referência a classe @see Periodo que representa o periodo
	 * em que o aluno ingressou na Universidade.
	 */
	@ManyToOne(optional=false)
	private Periodo periodo;
	
	/**
	 * Campo do tipo Enum que pode aceitar os tipos listados
	 * em @see FormaIngressoEnum. Esse campo é salvo no banco no
	 * formato texto.
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private FormaIngressoEnum formaIngresso;
	
	/**
	 * Referência a classe @see Curso.
	 */
	@ManyToOne
	private Curso curso;

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public FormaIngressoEnum getFormaIngresso() {
		return formaIngresso;
	}

	public void setFormaIngresso(FormaIngressoEnum formaIngresso) {
		this.formaIngresso = formaIngresso;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

}
