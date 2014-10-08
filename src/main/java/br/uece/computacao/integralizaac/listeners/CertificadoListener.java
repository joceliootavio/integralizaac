package br.uece.computacao.integralizaac.listeners;

import java.io.IOException;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;

import br.uece.computacao.integralizaac.entity.Certificado;
import br.uece.computacao.integralizaac.utils.CertificadoUtils;


/**
 * @author Jocélio Otávio
 *
 * Classe responsável por realizar procedimentos antes e
 * após persistir objetos do tipo @see Certificado.
 *
 */
public class CertificadoListener {
	
	/**
	 * Método que move da pasta temporária para a pasta definitiva
	 * os certificados persistidos pelo Aluno.
	 *
	 * @param entity the entity
	 */
	@PostPersist
	public void postPersist(Object entity) {
		if (entity instanceof Certificado) {
			Certificado certificado = (Certificado) entity;
			
			try {
				CertificadoUtils uploadUtils = new CertificadoUtils(certificado.getAtividadeAluno().getAluno());
				uploadUtils.persistirCertificado(certificado);
			} catch (IOException e) {
				System.out.println("Não foi possível persistir o certificado: " + certificado.getNome());
			}
		}
	}	
	
	/**
	 * Método que remove da pasta definitiva de certificados
	 * o certificado removido pelo Aluno.
	 *
	 * @param entity the entity
	 */
	@PostRemove
	public void postRemove(Object entity) {
		if (entity instanceof Certificado) {
			Certificado certificado = (Certificado) entity;			
			
			CertificadoUtils uploadUtils = new CertificadoUtils(certificado.getAtividadeAluno().getAluno());			
			uploadUtils.removerCertificado(certificado);
		}
	}
	
}
