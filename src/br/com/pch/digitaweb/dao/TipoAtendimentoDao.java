package br.com.pch.digitaweb.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.com.pch.digitaweb.modelo.TipoAtendimento;

public class TipoAtendimentoDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public TipoAtendimentoDao() {

	}

	public TipoAtendimentoDao(EntityManager manager) {
		this.em = manager;
	}

	public TipoAtendimento buscaPorId(Integer id) {
		TipoAtendimento t = em.find(TipoAtendimento.class, id);
		return t;
	}

	public List<TipoAtendimento> listaTodos() {
		CriteriaQuery<TipoAtendimento> query = em.getCriteriaBuilder().createQuery(TipoAtendimento.class);
		query.select(query.from(TipoAtendimento.class));
		return em.createQuery(query).getResultList();
	}

}
