package br.com.pch.digitaweb.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.pch.digitaweb.modelo.Prestador;

public class PrestadorDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	public PrestadorDao(){
		
	}

	public PrestadorDao(EntityManager manager) {
		this.em = manager;
	}
	
	public Prestador buscaPorId(Integer id) {
		Prestador prestador = em.find(Prestador.class, id);
		return prestador;
	}
	

}
