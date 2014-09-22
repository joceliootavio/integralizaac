package br.uece.computacao.integralizaac.listeners;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import br.uece.computacao.integralizaac.utils.ResourcesProvider;

public class IntegralizaAcContextListener implements ServletContextListener {
	
	private ResourcesProvider resourcesProvider;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			resourcesProvider = new ResourcesProvider();
			String pathCertificados = null;
			
			if (System.getenv("OPENSHIFT_DATA_DIR") != null) {
				pathCertificados = System.getenv("OPENSHIFT_DATA_DIR") + resourcesProvider.getValue("pathCertificados");
			} else {
				pathCertificados = resourcesProvider.getValue("pathCertificados");
			} 
			
			File diretorioCertificados = new File(pathCertificados);
			
			if (diretorioCertificados.mkdir()) {
				System.out.println("Diretório certificados foi criado.");
			} else { 
				System.out.println("Diretório certificados não foi criado.");
			}
				
			File novoDiretorio = new File(diretorioCertificados, "permanente");
			if (novoDiretorio.mkdir()) {
				System.out.println("Diretório permanente foi criado.");
			} else {
				System.out.println("Diretório permanente não foi criado.");
			}		
			
			novoDiretorio = new File(diretorioCertificados, "temp");
			if (novoDiretorio.mkdir()) {
				System.out.println("Diretório temp foi criado.");
			} else {
				System.out.println("Diretório temp não foi criado.");
			}
		} catch (Throwable t) {
			System.out.println("Erro ao tentar criar diretório para certificados");
			t.printStackTrace();
		} 
	}

}
