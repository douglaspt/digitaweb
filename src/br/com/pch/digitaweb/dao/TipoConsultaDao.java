package br.com.pch.digitaweb.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.com.pch.digitaweb.modelo.TipoConsulta;

public class TipoConsultaDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	public TipoConsultaDao(){
		
	}

	public TipoConsultaDao(EntityManager manager) {
		this.em = manager;
	}
	
	public TipoConsulta buscaPorId(Integer id) {
		TipoConsulta t = em.find(TipoConsulta.class, id);
		return t;
	}
	
	public List<TipoConsulta> listaTodos() {
		CriteriaQuery<TipoConsulta> query = em.getCriteriaBuilder().createQuery(TipoConsulta.class);
		query.select(query.from(TipoConsulta.class));
		return em.createQuery(query).getResultList();
	}

}
