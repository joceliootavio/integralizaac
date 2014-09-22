package br.uece.computacao.integralizaac.view;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import br.uece.computacao.integralizaac.exceptions.BusinessException;
import br.uece.computacao.integralizaac.exceptions.DAOException;

public class CustomExceptionHandler extends ExceptionHandlerWrapper {
	private ExceptionHandler wrapped;

	public CustomExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	@Override
	public void handle() throws FacesException {
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();

		for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents()
				.iterator(); i.hasNext();) {
			ExceptionQueuedEvent exceptionQueuedEvent = i.next();

			ExceptionQueuedEventContext exceptionQueuedEventContext = (ExceptionQueuedEventContext) exceptionQueuedEvent
					.getSource();

			Throwable throwable = exceptionQueuedEventContext.getException();

			Throwable buException = extractBusinessException(throwable);
			if (buException != null) {
				try {
					String message = buException.getMessage();
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					i.remove();
				}
			} else if (isDaoException(throwable)) {
				try {
					String message = throwable.getMessage();
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					i.remove();
				}
			} else if (throwable instanceof ViewExpiredException) {
				try {
					ViewExpiredException vee = (ViewExpiredException) throwable;

					if (vee.getViewId().indexOf("login") != -1) {
						externalContext.getRequestMap().put("currentViewId", vee.getViewId());
					} else {
						String path = ((javax.servlet.http.HttpServletRequest)externalContext.getRequest()).getContextPath();
						externalContext.redirect(path + "/pages/login.jsf");
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					i.remove();
				}
			}
		}
		getWrapped().handle();
	}

	private Throwable extractBusinessException(Throwable throwable) {
		try {
			if (throwable.getCause() == null) {
				return (throwable instanceof BusinessException) ? throwable : null;
			} else {
				return extractBusinessException(throwable.getCause());
			}
		} catch (NullPointerException e) {
			return null;
		}
	}

	private boolean isDaoException(Throwable throwable) {
		try {
			if (throwable instanceof DAOException) {
				return true;
			} else {
				return throwable.getCause() != null && isDaoException(throwable.getCause());
			}
		} catch (NullPointerException e) {
			return false;
		}
	}
}
