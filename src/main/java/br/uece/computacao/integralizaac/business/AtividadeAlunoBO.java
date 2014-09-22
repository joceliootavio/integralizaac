package br.uece.computacao.integralizaac.business;

import java.util.ArrayList;
import java.util.List;

import br.uece.computacao.integralizaac.dao.AtividadeAlunoDao;
import br.uece.computacao.integralizaac.dto.HorasAtividadeDTO;
import br.uece.computacao.integralizaac.dto.ListaDashboard;
import br.uece.computacao.integralizaac.entity.Aluno;
import br.uece.computacao.integralizaac.entity.AtividadeAluno;
import br.uece.computacao.integralizaac.enums.NaturezaEnum;

public class AtividadeAlunoBO extends Business<AtividadeAluno>{
	private AtividadeAlunoDao atividadeAlunoDao;
	
	public AtividadeAlunoBO(AtividadeAlunoDao dao) {
		super(dao);
		atividadeAlunoDao = dao;
	}

	public boolean excedeuHorasAtividade(AtividadeAluno atividadeAluno) {
		atividadeAlunoDao.flush();
		
		HorasAtividadeDTO dto = atividadeAlunoDao.horasAtividade(atividadeAluno);
		
		if (atividadeAluno.getAtividade().getMaximoHorasPorPeriodo() != null 
				&& dto.getSomaHorasPeriodo() > atividadeAluno.getAtividade().getMaximoHorasPorPeriodo()) {
			return true;
		}
		
		if (atividadeAluno.getAtividade().getMaximoHorasPorCurso() != null 
				&& dto.getSomaHorasCurso() > atividadeAluno.getAtividade().getMaximoHorasPorCurso()) {
			return true;
		}
		
		return false;
		
	}
	
	public List<NaturezaEnum> listarNaturezas(Aluno aluno) {
		if (aluno != null && aluno.getId() != null) {
			return atividadeAlunoDao.listarNaturezas(aluno);
		} else {
			return new ArrayList<NaturezaEnum>();
		} 
	}
	
	public ListaDashboard listarAtividadesPorNatureza(Aluno aluno, NaturezaEnum natureza) {
		if (aluno != null && aluno.getId() != null) {
			return atividadeAlunoDao.listarAtividadesPorNatureza(aluno, natureza);
		} else {
			return new ListaDashboard();
		} 
	}
	
	public List<AtividadeAluno> listarAtividades(Aluno aluno, Long ativComplementarId) {
		if (aluno != null && aluno.getId() != null) {
			return atividadeAlunoDao.listarAtividades(aluno, ativComplementarId);
		} else {
			return new ArrayList<AtividadeAluno>();
		} 
	}
	
}
