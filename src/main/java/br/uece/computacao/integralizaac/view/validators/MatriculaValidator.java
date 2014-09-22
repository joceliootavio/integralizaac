package br.uece.computacao.integralizaac.view.validators;

import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.hibernate.metamodel.ValidationException;

import br.uece.computacao.integralizaac.utils.MsgUtil;

@FacesValidator(value="matriculaValidator")
public class MatriculaValidator implements Validator {
	
	private static Pattern numericoRegex = Pattern.compile("^\\d?\\d[1-9]\\d{5}$");
	
	private MsgUtil msgUtil = new MsgUtil();
	
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		
		if (value != null) {
    		if (value.toString().length() < 7) {
    			throwError("aluno.erro.matriculaMenorQueMinimo");
    		}
    		
    		if (!numericoRegex.matcher(value.toString()).find()) {
    			throwError("aluno.erro.matriculaInvalida");
    		}
    	}
	}

	private void throwError(String msgKey) {
		String msg = msgUtil.getMessage(msgKey);
		
		FacesContext.getCurrentInstance().validationFailed();
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		
		throw new ValidationException(msg);
	}  
  
}  