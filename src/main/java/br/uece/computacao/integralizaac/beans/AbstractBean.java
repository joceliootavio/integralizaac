package br.uece.computacao.integralizaac.beans;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import br.uece.computacao.integralizaac.utils.MsgUtil;
import br.uece.computacao.integralizaac.utils.ResourcesProvider;

public class AbstractBean {

	private ResourcesProvider resourcesProvider;
	protected MsgUtil msgUtil;
	
	public AbstractBean() {
		resourcesProvider = new ResourcesProvider();
		msgUtil = new MsgUtil();
	}

	public boolean showPaginator(ArrayList<?> listaPaginada) {
		return listaPaginada != null && listaPaginada.size() > 10;
	}

	public void addErrorMessage(String idComponente, String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				msg, msg);
		FacesContext.getCurrentInstance().addMessage(idComponente, facesMsg);
	}

	public void addErrorMessageValue(String msg) {
		addErrorMessage(null, msg);
	}

	public void addErrorMessageKey(String msgKey) {
		addErrorMessage(null, msgUtil.getMessage(msgKey));
	}	

	public void addInfoMessage(String msgKey) {
		addInfoMessage(null, msgKey);
	}
	public void addInfoMessage(String id, String msgKey) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				msgUtil.getMessage(msgKey), msgUtil.getMessage(msgKey));
		FacesContext.getCurrentInstance().addMessage(id, facesMsg);
	}
	
	public void addAlertMessage(String msgKey) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN,
				msgUtil.getMessage(msgKey), msgUtil.getMessage(msgKey));
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}	

	protected ResourcesProvider getResourcesProvider() {
		return resourcesProvider;
	}

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
	
	protected void cleanSubmittedValues(String componentId) {
		UIComponent component = FacesContext.getCurrentInstance().getViewRoot().findComponent(componentId);
		
		cleanSubmittedValues(component);
	}

}