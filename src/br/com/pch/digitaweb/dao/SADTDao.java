package br.com.pch.digitaweb.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.com.pch.digitaweb.modelo.SADT;
import br.com.pch.digitaweb.tx.Transacional;

public class SADTDao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	public SADTDao(){
		
	}

	public SADTDao(EntityManager em) {
		this.em = em;
	}
	
	
	public void adiciona(SADT s) {
		em.persist(s);		
	}

	@Transacional
	public void remove(SADT s) {
		em.remove(em.merge(s));
	}
	
	public void atualiza(SADT s) {
		em.merge(s);
	}
	
	public List<SADT> listaTodos() {
		CriteriaQuery<SADT> query = em.getCriteriaBuilder().createQuery(SADT.class);
		query.select(query.from(SADT.class));

		List<SADT> lista = em.createQuery(query).setMaxResults(100).getResultList();
		return lista;
	}

	public SADT buscaPorId(long id) {
		SADT sadt = em.find(SADT.class, id);
		return sadt;
	}
	

}
