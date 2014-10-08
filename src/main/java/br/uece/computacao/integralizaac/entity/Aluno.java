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
	 * Campo texto obrigatório de 50 caracteres que guarda o 
	 * nome do Aluno.
	 */
	@Column(nullable=false, length=50)
	private String nome;
	
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
	 * Campo texto obrigatório de 30 caracteres que guarda
	 * o e-mail do aluno. Esse campo só deve aceitar emails
	 * institucionais. 
	 */
	@Column(nullable=false, length=30)
	private String email;
	
	/**
	 * Campo do tipo Enum que pode aceitar os tipos listados
	 * em @see FormaIngressoEnum. Esse campo é salvo no banco no
	 * formato texto.
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private FormaIngressoEnum formaIngresso;

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public FormaIngressoEnum getFormaIngresso() {
		return formaIngresso;
	}

	public void setFormaIngresso(FormaIngressoEnum formaIngresso) {
		this.formaIngresso = formaIngresso;
	}

}
