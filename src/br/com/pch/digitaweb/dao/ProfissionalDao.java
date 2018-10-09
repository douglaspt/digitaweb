package br.com.pch.digitaweb.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.pch.digitaweb.modelo.Profissional;

public class ProfissionalDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	public ProfissionalDao(){
		
	}

	public ProfissionalDao(EntityManager manager) {
		this.em = manager;
	}
	
	public Profissional buscaPorId(Integer id) {
		Profissional profissional = em.find(Profissional.class, id);
		return profissional;
	}
	

}
