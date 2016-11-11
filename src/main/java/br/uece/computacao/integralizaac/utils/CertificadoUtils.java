package br.uece.computacao.integralizaac.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.uece.computacao.integralizaac.entity.Aluno;
import br.uece.computacao.integralizaac.entity.AtividadeAluno;
import br.uece.computacao.integralizaac.entity.Certificado;
import br.uece.computacao.integralizaac.exceptions.BusinessException;

public class CertificadoUtils {

	private static final int DEFAULT_BUFFER_SIZE = 1024;
	
	private ResourcesProvider resourcesProvider;
	private File diretorioTempAluno;
	private File diretorioAluno;
	
	private Aluno aluno;
	
	public CertificadoUtils(Aluno aluno) {
		this.aluno = aluno;
		resourcesProvider = new ResourcesProvider();
	}
	
	public CertificadoUtils() {
		this(null);
	}	
	
	public String getPathTemp() {
		return resourcesProvider.getValue("pathCertificados") + "temp";
	}
	
	public String getPathPermanente() {
		return resourcesProvider.getValue("pathCertificados") + "permanente";
	}	

	public void getDiretorioTempCertificados() {
		if (diretorioTempAluno == null) {
			diretorioTempAluno = new File(getPathTemp(), aluno.getMatricula() );
			diretorioTempAluno.mkdir();
		}
	}
	
	
	public void handleFileUpload(AtividadeAluno atividadeAluno, FileUploadEvent event) throws IOException {
		String fileName = event.getFile().getFileName();
		
		for (Certificado certificado : atividadeAluno.getCertificados()) {
			if (fileName.equals(certificado.getNome())){
				throw new BusinessException("O arquivo informado já foi inserido.");
			}
		}
		
		if (atividadeAluno.getCertificados().size() == 3) {
			throw new BusinessException("Só é permitido inserir até 3 arquivos.");
		}
		
		salvaCertificadoTemp(event);
		
		new Certificado(atividadeAluno, fileName);
	}
	
	public void salvaCertificadoTemp(FileUploadEvent event) throws IOException {
		getDiretorioTempCertificados();
		
		File arquivoNovo = new File(diretorioTempAluno, event.getFile().getFileName());
		arquivoNovo.createNewFile();
		
		FileUtils.copy(event.getFile().getInputstream(), arquivoNovo);
		System.out.println("Upload do arquivo: " + event.getFile().getFileName());
	}
	
	public void getDiretorioCertificados() {
		if (diretorioAluno == null) {
			diretorioAluno = new File(getPathPermanente(), aluno.getMatricula());
			diretorioAluno.mkdir();			
		}
	}
	
	public void downloadCertificado(Certificado certificado) throws Exception {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        
        getDiretorioCertificados();

        File file = null;
        if (certificado.isPesistido()) {
        	file = new File(diretorioAluno, certificado.getNomeArquivoPersistido());
        } else {
        	file = new File(diretorioTempAluno, certificado.getNome());
        }
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);

            response.reset();
            if (".pdf".equals(certificado.getExtensao()))
            	response.setHeader("Content-Type", "application/pdf");
            else 
            	response.setHeader("Content-Type", "image/jpeg");
            	
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "inline; filename=\"" + certificado.getNome() + "\"");
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            output.flush();
        } finally {
            close(output);
            close(input);
        }

        facesContext.responseComplete();
    }
	
	public StreamedContent gerarMediaCertificado(Certificado certificado) throws Exception {
        getDiretorioCertificados();

        File file = null;
        if (certificado.isPesistido()) {
        	file = new File(diretorioAluno, certificado.getNomeArquivoPersistido());
        } else {
        	file = new File(diretorioTempAluno, certificado.getNome());
        }
        InputStream input = null;
        
        try {
            input = new FileInputStream(file);
            
            return new DefaultStreamedContent(input, "application/pdf");
        } finally {
            close(input);
        }
    }
	
    private void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	public void persistirCertificado(Certificado certificado) throws IOException {
		getDiretorioTempCertificados();
		getDiretorioCertificados();
		
		File arquivoNovo = new File(diretorioAluno, certificado.getNomeArquivoPersistido());
		arquivoNovo.createNewFile();
		
		File arquivoTemp = new File(diretorioTempAluno, certificado.getNome());
		
		FileUtils.copy(arquivoTemp, arquivoNovo);
		
		FileUtils.deleteQuietly(arquivoTemp);
		
		System.out.println("Arquivo persistido: " + arquivoNovo.getName());
	}

	public void removerCertificado(Certificado certificado) {
		File arquivo = new File(diretorioAluno, certificado.getNomeArquivoPersistido());		
		FileUtils.deleteQuietly(arquivo);
	}
	
	public void removerCertificadoDaTemp(Certificado certificado) {
		getDiretorioTempCertificados();
		
		File arquivo = new File(diretorioTempAluno, certificado.getNomeArquivoPersistido());		
		FileUtils.deleteQuietly(arquivo);
	}
}
