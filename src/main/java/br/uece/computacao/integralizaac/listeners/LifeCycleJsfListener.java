package br.uece.computacao.integralizaac.listeners;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class LifeCycleJsfListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	public void beforePhase(PhaseEvent event) {
		System.out.println("START PHASE " + event.getPhaseId());
	}

	public void afterPhase(PhaseEvent event) {
		System.out.println("END PHASE " + event.getPhaseId());
	}

}
