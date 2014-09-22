package br.uece.computacao.integralizaac.view.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.uece.computacao.integralizaac.business.UsuarioBO;
import br.uece.computacao.integralizaac.dao.UsuarioDao;
import br.uece.computacao.integralizaac.entity.Aluno;
import br.uece.computacao.integralizaac.services.EmailService;

@FacesConverter(value="alunoConverter")
public class AlunoConverter implements Converter {  
	
	private UsuarioBO usuarioBO;
	
	public AlunoConverter() {
		usuarioBO = new UsuarioBO(new UsuarioDao(), new EmailService());
	}
    
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {  
        if (value != null) {  
            return usuarioBO.buscaAlunoPorId(Long.parseLong(value));  
        }  
        return null;  
    }  
  
    public String getAsString(FacesContext ctx, UIComponent component, Object value) {  
  
        if (value != null) {  
        	Aluno aluno = (Aluno) value;
            if (aluno.getId() != null) {  
                return String.valueOf(aluno.getId());  
            }  
        }  
  
        return (String) value;  
    }  
  
}  