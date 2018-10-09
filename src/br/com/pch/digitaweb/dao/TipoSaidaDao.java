package br.com.pch.digitaweb.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.com.pch.digitaweb.modelo.TipoSaida;

public class TipoSaidaDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	public TipoSaidaDao(){
		
	}

	public TipoSaidaDao(EntityManager manager) {
		this.em = manager;
	}
	
	public TipoSaida buscaPorId(Integer id) {
		TipoSaida t = em.find(TipoSaida.class, id);
		return t;
	}
	
	public List<TipoSaida> listaTodos() {
		CriteriaQuery<TipoSaida> query = em.getCriteriaBuilder().createQuery(TipoSaida.class);
		query.select(query.from(TipoSaida.class));
		return em.createQuery(query).getResultList();
	}
	

}
