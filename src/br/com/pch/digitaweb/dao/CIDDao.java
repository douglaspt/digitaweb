package br.com.pch.digitaweb.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.pch.digitaweb.modelo.CID;

public class CIDDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	public CIDDao(){
		
	}

	public CIDDao(EntityManager manager) {
		this.em = manager;
	}
	
	public CID buscaPorId(String id) {
		CID c = em.find(CID.class, id);
		return c;
	}
	

}
