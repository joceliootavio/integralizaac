package br.uece.computacao.integralizaac.beans;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import br.uece.computacao.integralizaac.utils.MsgUtil;
import br.uece.computacao.integralizaac.utils.ResourcesProvider;

/**
 * @author Jocélio Otávio
 * 
 * Classe abstrata da qual deve herdar todas as classes Bean
 * que representam os beans gerenciados pelo JSF na
 * aplicação. Essa classe possui métodos utilitários 
 * prontos que serão reaproveitados nas classes filhas. 
 *
 */
public class AbstractBean {

	/**
	 * Objeto da classe responsável pelas configurações 
	 * de recursos da aplicação.
	 */
	private ResourcesProvider resourcesProvider;
	
	/**
	 * Objeto da classe de mensagens da aplicação.
	 */
	protected MsgUtil msgUtil;
	
	public AbstractBean() {
		resourcesProvider = new ResourcesProvider();
		msgUtil = new MsgUtil();
	}

	/**
	 * Método que retorna <code>true</code> quando a lista
	 * passada como parâmetro atender ao requisito de 
	 * exibição da paginação na camada visual.
	 *  
	 * @param listaPaginada Lista que deve ou não ser paginada.
	 * 
	 * @return
	 */
	public boolean showPaginator(ArrayList<?> listaPaginada) {
		return listaPaginada != null && listaPaginada.size() > 10;
	}

	/**
	 * Método que adiciona mensagem de erro a um componente 
	 * específico do JSF.
	 * 
	 * @param idComponente ClientId do compontente, quando nulo
	 * será exibido na mensagem global.
	 * 
	 * @param msg Texto da mensagem a ser exibida.
	 */
	public void addErrorMessage(String idComponente, String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				msg, msg);
		FacesContext.getCurrentInstance().addMessage(idComponente, facesMsg);
	}

	/**
	 * Método que adiciona mensagem de erro global do JSF.
	 * 
	 * @param msg Texto da mensagem a ser exibida.
	 */
	public void addErrorMessageValue(String msg) {
		addErrorMessage(null, msg);
	}

	/**
	 * Método que adiciona mensagem de erro global do JSF 
	 * a partir da chave da mensagem correspondente ao 
	 * arquivo de mensagens da aplicação.
	 * 
	 * @param msgKey	Chave da mensagem da aplicação.
	 */
	public void addErrorMessageKey(String msgKey) {
		addErrorMessage(null, msgUtil.getMessage(msgKey));
	}	

	/**
	 * Método que adiciona mensagem de alerta global do JSF 
	 * a partir da chave da mensagem correspondente ao 
	 * arquivo de mensagens da aplicação.
	 * 
	 * @param msgKey	Chave da mensagem da aplicação.
	 */
	public void addInfoMessage(String msgKey) {
		addInfoMessage(null, msgKey);
	}
	
	/**
	 * Método que adiciona mensagem informativa a um componente 
	 * específico do JSF a partir da chave da mensagem correspondente 
	 * ao arquivo de mensagens da aplicação.
	 * 
	 * @param idComponente ClientId do compontente, quando nulo
	 * será exibido na mensagem global.
	 * 
	 * @param msgKey	Chave da mensagem da aplicação.
	 */
	public void addInfoMessage(String id, String msgKey) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				msgUtil.getMessage(msgKey), msgUtil.getMessage(msgKey));
		FacesContext.getCurrentInstance().addMessage(id, facesMsg);
	}
	
	/**
	 * Método que adiciona mensagem de alerta a um componente 
	 * específico do JSF a partir da chave da mensagem correspondente 
	 * ao arquivo de mensagens da aplicação.
	 * 
	 * @param msgKey	Chave da mensagem da aplicação.
	 */	
	public void addAlertMessage(String msgKey) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN,
				msgUtil.getMessage(msgKey), msgUtil.getMessage(msgKey));
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}	

	/**
	 * Métoodo que retorna a instância da classe de acesso
	 * as configurações de recursos da aplicação.
	 * 
	 * @return Instância da classe @see ResourcesProvider
	 */
	protected ResourcesProvider getResourcesProvider() {
		return resourcesProvider;
	}

	/**
	 * Método que limpa a árvore de componentes hierarquicamente
	 * abaixo do componente JSF passado como parâmetro.
	 * 
	 * @param component Componente pai.
	 */
	protected void cleanSubmittedValues(UIComponent component) {
		if (component != null) {
			if (component instanceof EditableValueHolder) {
				EditableValueHolder evh = (EditableValueHolder) component;
				evh.setSubmittedValue(null);
				evh.setValue(null);
				evh.setLocalValueSet(false);
				evh.setValid(true);
			}
			if (component.getChildCount() > 0) {
				for (UIComponent child : component.getChildren()) {
					cleanSubmittedValues(child);
				}
			}
		}
	}
	
	/**
	 * Método que limpa o cache de valores enviados na 
	 * requisição anterior.
	 * 
	 * @param componentId ClientId do Componente pai.
	 */
	protected void cleanSubmittedValues(String componentId) {
		UIComponent component = FacesContext.getCurrentInstance().getViewRoot().findComponent(componentId);
		
		cleanSubmittedValues(component);
	}

}