package br.uece.computacao.integralizaac.listeners;

import java.io.IOException;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;

import br.uece.computacao.integralizaac.entity.Certificado;
import br.uece.computacao.integralizaac.utils.CertificadoUtils;


public class CertificadoListener {
	
	/**
	 * Post persist.
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
	 * Post remove.
	 *
	 * @param entity the entity
	 */
	@PostRemove
	public void postRemove(Object entity) {
		if (entity instanceof Certificado) {
			Certificado certificado = (Certificado) entity;			
			System.out.println("Depois de removido");
			
			CertificadoUtils uploadUtils = new CertificadoUtils(certificado.getAtividadeAluno().getAluno());			
			uploadUtils.removerCertificado(certificado);
		}
	}
	
}
