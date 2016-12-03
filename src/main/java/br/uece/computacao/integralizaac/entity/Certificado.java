package br.uece.computacao.integralizaac.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;

import br.uece.computacao.integralizaac.listeners.CertificadoListener;

/**
 * @author Jocélio Otávio
 *
 * Classe que representa a entidade Certificado.
 */
@Entity
@EntityListeners(CertificadoListener.class)
public class Certificado extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Referência a classe @see AtividadeAluno.
	 */
	@ManyToOne
	private AtividadeAluno atividadeAluno;
	
	/**
	 * Campo texto obrigatório que guarda o nome do certificado
	 * extraído do arquivo anexado pelo Aluno.
	 */
	@Column(nullable=false)
	private String nome;
	
	/**
	 * Extensão do arquivo anexado pelo aluno que representa
	 * digitalmente o certificado.
	 */
	@Column(nullable=false)
	private String extensao;
	
	public Certificado() {
		super();
	}
	
	/**
	 * Construtor que recebe como parâmetro a atividade do aluno
	 * a qual o certificado será adicionado, e o nome do arquivo
	 * que é a versão digital do certificado.
	 * 
	 * @param atividade Atividade do aluno
	 * @param nome Nome do arquivo
	 */
	public Certificado(AtividadeAluno atividade, String nome) {
		this.nome = nome;
		this.atividadeAluno = atividade;
		
		atividade.getCertificados().add(this);
		
		identificarExtensao(nome);
	}

	/**
	 * Método responsável por identificar a extensão de um arquivo
	 * a partir do nome desse arquivo passado como parâmetro.
	 * 
	 * @param nome Nome do arquivo.
	 */
	private void identificarExtensao(String nome) {
		int indexPonto = nome.lastIndexOf('.');
		String extensaoArquivo = null;
		if (indexPonto != -1) {
			extensaoArquivo = nome.substring(indexPonto);
		}
		this.extensao = extensaoArquivo;
	}
	
	/**
	 * Nome do arquivo após ser persistido no banco. O nome do
	 * arquivo persistido é igual ao id do certificado concatenado
	 * com a extensão.  
	 * 
	 * @return
	 */
	public String getNomeArquivoPersistido() {
       	return getId() + getExtensao();
	}
	
	public AtividadeAluno getAtividadeAluno() {
		return atividadeAluno;
	}

	public String getNome() {
		return nome;
	}

	public String getExtensao() {
		return extensao;
	}

	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}

	public void setAtividadeAluno(AtividadeAluno atividadeAluno) {
		this.atividadeAluno = atividadeAluno;
	}

}
