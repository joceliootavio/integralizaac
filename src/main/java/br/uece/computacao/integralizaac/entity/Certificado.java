package br.uece.computacao.integralizaac.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;

import br.uece.computacao.integralizaac.listeners.CertificadoListener;

@Entity
@EntityListeners(CertificadoListener.class)
public class Certificado extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private AtividadeAluno atividadeAluno;
	
	@Column(nullable=false)
	private String nome;
	
	@Column(nullable=false)
	private String extensao;
	
	public Certificado() {
		super();
	}
	
	public Certificado(AtividadeAluno atividade, String nome) {
		this.nome = nome;
		this.atividadeAluno = atividade;
		
		atividade.getCertificados().add(this);
		
		identificarExtensao(nome);
	}

	private void identificarExtensao(String nome) {
		int indexPonto = nome.lastIndexOf('.');
		String extensaoArquivo = null;
		if (indexPonto != -1) {
			extensaoArquivo = nome.substring(indexPonto);
		}
		this.extensao = extensaoArquivo;
	}
	
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

}
